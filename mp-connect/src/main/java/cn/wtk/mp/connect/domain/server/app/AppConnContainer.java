package cn.wtk.mp.connect.domain.server.app;

import cn.wtk.mp.connect.domain.server.ConnContainer;
import cn.wtk.mp.connect.domain.server.app.connector.Connector;
import cn.wtk.mp.connect.domain.server.app.connector.ConnectorKey;
import cn.wtk.mp.connect.domain.server.app.connector.connection.Connection;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Slf4j
public class AppConnContainer implements ConnContainer {
    @Getter
    private final Long appId;
    @Getter
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Map<Serializable, Connector> connectors = new ConcurrentHashMap<>();

    public AppConnContainer(Long appId) {
        this.appId = appId;
    }

    public int getConnectorSize() {
        return connectors.size();
    }

    @Override
    public void addConn(Connection conn) {
        ConnectorKey connectorKey = conn.getConnectorKey();
        Serializable connectorId = connectorKey.getConnectorId();
        AtomicBoolean create = new AtomicBoolean(false);
        connectors.compute(connectorId, (k, connector) -> {
            if (connector == null) {
                connector = new Connector(connectorKey);
                create.set(true);
            }
            connector.addConn(conn);
            return connector;
        });
        if (create.get()) {

        }
    }


    @Override
    public Connection removeConn(ConnectorKey connectorKey, UUID connId) {
        Serializable connectorId = connectorKey.getConnectorId();
        // 要获取lambda表达式里的变量需要使用Atomic对象
        AtomicReference<Connection> connRef = new AtomicReference<>();
        connectors.computeIfPresent(connectorId, (k, v) -> {
            connRef.set(v.removeConn(connId));
            // 如果connector的连接数为0，则return null使其从map中移出
            if (v.getConnSize() == 0) {
                return null;
            } else {
                return v;
            }
        });
        return connRef.get();
    }

    @Override
    public Connection getConn(ConnectorKey connectorKey, UUID connId) {
        Connector connector = connectors.get(connectorKey.getConnectorId());
        if (connector == null) {
            return null;
        }
        return connector.getConn(connId);
    }

    public Connector getConnector(ConnectorKey connectorKey) {
        return connectors.get(connectorKey.getConnectorId());
    }

    public void addAll(AppConnContainer other) {
        this.connectors.putAll(other.connectors);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppConnContainer that = (AppConnContainer) o;
        return appId.equals(that.appId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appId);
    }
}
