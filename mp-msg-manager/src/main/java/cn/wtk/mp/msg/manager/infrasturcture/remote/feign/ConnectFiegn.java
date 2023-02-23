package cn.wtk.mp.msg.manager.infrasturcture.remote.feign;

import cn.wtk.mp.msg.manager.infrasturcture.client.dto.MsgPushDTO;
import cn.wtk.mp.msg.manager.infrasturcture.config.lb.MsgRouteHandler;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author wtk
 * @date 2023-02-18
 */
@FeignClient(
        value = "mp-connect",
        path = "/connect"
)
public interface ConnectFiegn {

    void pushMsg(MsgPushDTO msg,
                 @RequestHeader(MsgRouteHandler.MSG_ROUTE_IP) String ip,
                 @RequestHeader(MsgRouteHandler.MSG_ROUTE_PORT) Integer rcvrPort
    );
}
