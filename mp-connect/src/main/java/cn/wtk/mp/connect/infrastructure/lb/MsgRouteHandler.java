package cn.wtk.mp.connect.infrastructure.lb;

import cn.wtk.mp.common.base.exception.internal.BugException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/6
 */
@Component
@Slf4j
public class MsgRouteHandler {

    public static final String HEADER_NAME_MSG_ROUTE_IP = "msg-route-ip";
    public static final String HEADER_NAME_MSG_ROUTE_PORT = "msg-route-port";

    public void setMsgRouteHeader() {

    }

    public boolean needMsgRoute(Request request) {
        HttpHeaders headers = ((RequestDataContext) request.getContext()).getClientRequest().getHeaders();
        List<String> serverIp = headers.get(HEADER_NAME_MSG_ROUTE_IP);
        List<String> serverPort = headers.get(HEADER_NAME_MSG_ROUTE_PORT);
        if (serverIp == null && serverPort == null) {
            return false;
        }
        if (serverIp == null || serverPort == null || serverIp.size() != 1 || serverPort.size() != 1) {
            log.warn("用于消息路由的 ip 和 port 出现多个：IPs: {}, ports: {}", serverIp, serverPort);
            return false;
        }
        return true;
    }
}
