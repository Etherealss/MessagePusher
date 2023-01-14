package cn.wtk.mp.connect.domain.server.app.connector;

import cn.wtk.mp.connect.domain.server.ConnComposite;
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
public class Connector implements ConnComposite {
    private final ConnectorKey connectorKey;
    private final Map<UUID, Connection> conns = new ConcurrentHashMap<>();

    public Connector(ConnectorKey connectorKey) {
        this.connectorKey = connectorKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connector connector = (Connector) o;
        return connectorKey.equals(connector.connectorKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectorKey);
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
