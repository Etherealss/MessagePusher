package cn.wtk.mp.connect.domain.conn.server.connector;

import cn.wtk.mp.connect.domain.conn.server.connector.connection.Connection;
import lombok.Getter;
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
public class Connector {

    private final Map<UUID, Connection> conns = new ConcurrentHashMap<>();
    @Getter
    private final Long connectorId;
    @Getter
    private final Long appId;

    public int getConnSize() {
        return conns.size();
    }

    public Connector(Long connectorId, Long appId) {
        this.connectorId = connectorId;
        this.appId = appId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connector)) return false;
        Connector connector = (Connector) o;
        return connectorId.equals(connector.connectorId) && appId.equals(connector.appId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectorId, appId);
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
