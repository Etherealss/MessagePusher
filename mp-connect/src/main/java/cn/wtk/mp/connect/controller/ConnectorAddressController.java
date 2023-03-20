package cn.wtk.mp.connect.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.connect.application.connector.RouteAddressAppService;
import cn.wtk.mp.connect.infrastructure.client.command.BatchConnectorIdQuery;
import cn.wtk.mp.connect.infrastructure.client.dto.ConnectorAddressDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wtk
 * @date 2023-01-06
 */
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@ResponseAdvice
public class ConnectorAddressController {

    private final RouteAddressAppService routeAddressAppService;

    @GetMapping("/connectors/{connectorId}/address")
    public ConnectorAddressDTO getConnectorAddress(@PathVariable Long connectorId) {
        return routeAddressAppService.getConnectorRouteAddress(connectorId);
    }

    @GetMapping("/list/connectors/address")
    public List<ConnectorAddressDTO> getConnectorAddresses(@RequestBody BatchConnectorIdQuery query) {
        return routeAddressAppService.getConnectorRouteAddress(query.getConnectorIds());
    }
}
