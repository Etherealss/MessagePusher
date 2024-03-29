package cn.wtk.mp.connect.application.connector;

import cn.wtk.mp.connect.domain.conn.server.connector.RouteAddressManager;
import cn.wtk.mp.connect.infrastructure.client.dto.ConnectorAddressDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/24
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RouteAddressAppService {
    private final RouteAddressManager routeAddressManager;

    public ConnectorAddressDTO getConnectorRouteAddress(Long connectorId) {
        return routeAddressManager.getConnectorRouteAddress(connectorId);
    }

    public List<ConnectorAddressDTO> getConnectorRouteAddress(List<Long> connectorIds) {
        return routeAddressManager.getConnectorRouteAddresses(connectorIds);
    }
}
