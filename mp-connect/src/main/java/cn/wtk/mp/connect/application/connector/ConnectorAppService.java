package cn.wtk.mp.connect.application.connector;

import cn.wtk.mp.connect.domain.conn.server.connector.ConnectorService;
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

    public Long registerConnector() {
        return connectorService.createConnector();
    }
}
