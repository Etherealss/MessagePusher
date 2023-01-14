package cn.wtk.mp.connect.domain.server.app;

import cn.wtk.mp.connect.domain.server.ConnComposite;
import cn.wtk.mp.connect.domain.server.app.connector.Connector;
import cn.wtk.mp.connect.domain.server.app.connector.ConnectorKey;
import cn.wtk.mp.connect.domain.server.app.connector.connection.Connection;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Slf4j
public class AppConnManager implements ConnComposite {
    private final Long appId;
    private final Map<UUID, Connector> connectors = new ConcurrentHashMap<>();

    public AppConnManager(Long appId) {
        this.appId = appId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppConnManager that = (AppConnManager) o;
        return appId.equals(that.appId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId);
    }

    @Override
    public void addConn(Connection conn) {

    }

    @Override
    public Connection removeConn(ConnectorKey connectorKey, UUID connId) {
        return null;
    }

    @Override
    public Connection getConn(ConnectorKey connectorKey, UUID connId) {
        return null;
    }

    @Override
    public Map<UUID, Connection> getConns(ConnectorKey connectorKey) {
        return null;
    }

    @Override
    public boolean containsConn(ConnectorKey connectorKey) {
        return false;
    }
}
