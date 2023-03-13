package cn.wtk.mp.connect.domain.conn.server.connector;

import cn.wtk.mp.connect.domain.conn.server.connector.device.DeviceConnection;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Slf4j
public class Connector {

    private final Map<Long, DeviceConnection> conns = new ConcurrentHashMap<>();
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

    public void addConn(DeviceConnection conn) {
        conns.put(conn.getDeviceId(), conn);
    }

    public DeviceConnection removeConn(Long connId) {
        return conns.remove(connId);
    }

    public DeviceConnection getConn(Long connId) {
        return conns.get(connId);
    }

    public boolean containsConn(Long connId) {
        return conns.containsKey(connId);
    }

    public void addAll(Connector other) {
        this.conns.putAll(other.conns);
    }
}
