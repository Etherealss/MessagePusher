package cn.wtk.mp.msg.manager.infrasturcture.remote.feign.lb;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/6
 */
@Component
@Slf4j
public class MsgRouteHandler {

    public static final String MSG_ROUTE_IP = "x-msg-route-ip";
    public static final String MSG_ROUTE_PORT = "x-msg-route-port";

    public void setMsgRouteHeader(@NonNull HttpHeaders headers, String ip, int port) {
        headers.set(MSG_ROUTE_IP, ip);
        headers.set(MSG_ROUTE_PORT, String.valueOf(port));
    }

    @Nullable
    public MsgRouteAddress getMsgRouteAddress(@NonNull HttpHeaders headers) {
        List<String> serverIp = headers.get(MSG_ROUTE_IP);
        List<String> serverPort = headers.get(MSG_ROUTE_PORT);
        if (serverIp == null && serverPort == null) {
            return null;
        }
        if (serverIp == null || serverPort == null || serverIp.size() != 1 || serverPort.size() != 1) {
            log.warn("用于消息路由的 ip 和 port 出现多个：IPs: {}, ports: {}", serverIp, serverPort);
            return null;
        }
        return new MsgRouteAddress(serverIp.get(0), Integer.parseInt(serverPort.get(0)));
    }
}
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
class MsgRouteAddress {
    String ip;
    int port;
}