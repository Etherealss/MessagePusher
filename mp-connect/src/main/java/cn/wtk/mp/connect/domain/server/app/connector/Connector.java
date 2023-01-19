package cn.wtk.mp.connect.domain.server.app.connector;

import cn.wtk.mp.connect.domain.server.app.connector.connection.Connection;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Slf4j
public class Connector {

    private final Map<UUID, Connection> conns = new ConcurrentHashMap<>();
    @Getter
    private final ConnectorKey connectorKey;
    @Getter
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public int getConnSize() {
        return conns.size();
    }

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

    public void addConn(Connection conn) {
        conns.put(conn.getConnId(), conn);
    }

    public Connection removeConn(UUID connId) {
        return conns.remove(connId);
    }

    public Connection getConn(UUID connId) {
        return conns.get(connId);
    }

    public boolean containsConn(UUID connId) {
        return conns.containsKey(connId);
    }

    public void addAll(Connector other) {
        this.conns.putAll(other.conns);
    }
}
