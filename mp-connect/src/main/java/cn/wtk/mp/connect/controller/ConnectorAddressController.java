package cn.wtk.mp.connect.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.annotation.InternalAuth;
import cn.wtk.mp.common.security.service.auth.server.ServerSecurityContextHolder;
import cn.wtk.mp.connect.application.connector.RouteAddressAppService;
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

    @PutMapping("/connectors/{connectorId}/addresses/connect")
    @InternalAuth
    public ConnectorAddressDTO updateConnectAddress4(@PathVariable Long connectorId) {
        return routeAddressAppService.getAddress4Connect(
                ServerSecurityContextHolder.require().getServerId(), connectorId
        );
    }

    @GetMapping("/connectors/{connectorId}/addresses/routes")
    @InternalAuth
    public ConnectorAddressDTO getRouteAddress(@PathVariable Long connectorId) {
        return routeAddressAppService.getConnectorRouteAddress(connectorId);
    }

    @GetMapping("/list/connectors/addresses/routes")
    @InternalAuth
    public List<ConnectorAddressDTO> getRouteAddresses(@RequestParam List<Long> ids) {
        return routeAddressAppService.getConnectorRouteAddress(ids);
    }
}
