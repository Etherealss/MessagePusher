package cn.wtk.mp.connect.application.connector;

import cn.wtk.mp.connect.domain.conn.server.connector.ConnectorAddressManager;
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
    private final ConnectorAddressManager connectorAddressManager;

    public ConnectorAddressDTO getAddress4Connect(Long appId, Long connectorId) {
        return connectorAddressManager.getAddress4Connect(
                appId, connectorId
        );
    }

    public ConnectorAddressDTO getConnectorRouteAddress(Long connectorId) {
        return connectorAddressManager.getConnectorRouteAddress(connectorId);
    }

    public List<ConnectorAddressDTO> getConnectorRouteAddress(List<Long> connectorIds) {
        return connectorAddressManager.getConnectorRouteAddresses(connectorIds);
    }
}
