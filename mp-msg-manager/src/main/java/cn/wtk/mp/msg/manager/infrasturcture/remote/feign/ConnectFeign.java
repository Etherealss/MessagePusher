package cn.wtk.mp.msg.manager.infrasturcture.remote.feign;

import cn.wtk.mp.msg.manager.infrasturcture.config.lb.MsgRouteHandler;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.command.MultiMsgPushCommand;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.connect.ConnectorAddressDTO;
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
public interface ConnectFeign {

    /**
     * @see MsgRouteHandler 路由逻辑
     * @param msg
     * @param routeAddress
     */
    @PostMapping("/list/connectors/msgs")
    void pushMsg(@RequestBody @Validated MultiMsgPushCommand msg,
                 @RequestHeader(MsgRouteHandler.MSG_ROUTE_ADDRESS) String routeAddress
    );

    @GetMapping("/connectors/{connectorId}/addresses/routes")
    ConnectorAddressDTO getConnectorAddress(@PathVariable Long connectorId);

    @GetMapping("/list/connectors/addresses/routes")
    List<ConnectorAddressDTO> getConnectorAddress(@RequestParam List<Long> ids);
}
