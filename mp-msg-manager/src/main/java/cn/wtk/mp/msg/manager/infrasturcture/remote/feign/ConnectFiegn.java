package cn.wtk.mp.msg.manager.infrasturcture.remote.feign;

import cn.wtk.mp.msg.manager.infrasturcture.config.lb.MsgRouteHandler;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.command.MultiMsgPushCommand;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.connect.ConnectorAddressDTO;
import cn.wtk.mp.msg.manager.infrasturcture.remote.feign.query.BatchConnectorIdQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wtk
 * @date 2023-02-18
 */
@FeignClient(
        value = "mp-connect",
        path = "/connect"
)
public interface ConnectFiegn {

    @PostMapping("/msg/action/push")
    void pushMsg(@RequestBody @Validated MultiMsgPushCommand msg,
                 @RequestHeader(MsgRouteHandler.MSG_ROUTE_IP) String ip,
                 @RequestHeader(MsgRouteHandler.MSG_ROUTE_PORT) Integer rcvrPort
    );

    @GetMapping("/connectors/{connectorId}/address")
    ConnectorAddressDTO getConnectorAddress(@PathVariable Long connectorId);

    @GetMapping("/list/connectors/address")
    List<ConnectorAddressDTO> getConnectorAddress(@RequestBody BatchConnectorIdQuery query);
}
