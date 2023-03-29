package cn.wtk.mp.msg.manager.infrasturcture.config.lb;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/6
 */
@Component
@Slf4j
public class MsgRouteHandler {

    public static final String MSG_ROUTE_ADDRESS = "x-msg-route-addresss";

    @Nullable
    public MsgRouteAddress getMsgRouteAddress(@NonNull HttpHeaders headers) {
        List<String> routeServerIpList = headers.get(MSG_ROUTE_ADDRESS);
        if (CollectionUtils.isEmpty(routeServerIpList)) {
            log.warn("远程调用时缺少消息路由的地址");
            return null;
        }
        if (CollectionUtils.isEmpty(routeServerIpList) || routeServerIpList.size() != 1) {
            log.warn("远程调用时路由地址存在多个: {}", routeServerIpList);
            return null;
        }
        String address = routeServerIpList.get(0);
        String[] split = address.split(":");
        if (split.length!= 2) {
            log.warn("远程调用时路由地址格式错误: {}", address);
            return null;
        }
        try {
            return new MsgRouteAddress(split[1], Integer.parseInt(split[1]));
        } catch (NumberFormatException e) {
            log.warn("远程调用时路由地址格式错误: {}", address, e);
            return null;
        }
    }
}
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
class MsgRouteAddress {
    String ip;
    int port;
}