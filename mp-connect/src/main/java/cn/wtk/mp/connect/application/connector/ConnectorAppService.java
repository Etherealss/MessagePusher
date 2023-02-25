package cn.wtk.mp.connect.application.connector;

import cn.wtk.mp.connect.domain.server.connector.ConnectorService;
import cn.wtk.mp.connect.domain.server.connector.device.ConnectorDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @date 2023-02-25
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectorAppService {
    private final ConnectorService connectorService;
    private final ConnectorDeviceService connectorDeviceService;

    public Long registerConnector() {
        return connectorService.createConnector();
    }

    public Long registerConnectorDevice(Long connectorId) {
        return connectorService.createConnector();
    }
}
