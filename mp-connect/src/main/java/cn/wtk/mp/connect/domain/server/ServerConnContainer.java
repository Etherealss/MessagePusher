package cn.wtk.mp.connect.domain.server;

import cn.wtk.mp.connect.domain.server.app.AppConnContainer;
import cn.wtk.mp.connect.domain.server.app.connector.ConnectorKey;
import cn.wtk.mp.connect.domain.server.app.connector.connection.Connection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author wtk
 * @date 2023-01-06
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ServerConnContainer implements ConnContainer {

    private final Map<Long, AppConnContainer> apps = new ConcurrentHashMap<>();

    @Override
    public void addConn(Connection conn) {
        Long appId = conn.getConnectorKey().getAppId();
        AppConnContainer appConnContainer = apps.get(appId);
        apps.compute(appId, (key, app) -> {
            if (app == null) {
                app = new AppConnContainer(appId);
            }
            app.addConn(conn);
            return app;
        });
    }

    @Override
    public Connection removeConn(ConnectorKey connectorKey, UUID connId) {
        Long appId = connectorKey.getAppId();
        AtomicReference<Connection> connRef = new AtomicReference<>();
        apps.computeIfPresent(appId, (key, app) -> {
            connRef.set(app.removeConn(connectorKey, connId));
            if (app.getConnectorSize() == 0) {
                return null;
            } else {
                return app;
            }
        });
        return connRef.get();
    }

    @Override
    public Connection getConn(ConnectorKey connectorKey, UUID connId) {
        AppConnContainer appConnContainer = this.apps.get(connectorKey.getAppId());
        return appConnContainer.getConn(connectorKey, connId);
    }
}
