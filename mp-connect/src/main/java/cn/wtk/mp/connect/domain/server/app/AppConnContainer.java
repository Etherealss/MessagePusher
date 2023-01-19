package cn.wtk.mp.connect.domain.server.app;

import cn.wtk.mp.connect.domain.server.ConnContainer;
import cn.wtk.mp.connect.domain.server.app.connector.Connector;
import cn.wtk.mp.connect.domain.server.app.connector.ConnectorKey;
import cn.wtk.mp.connect.domain.server.app.connector.connection.Connection;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Slf4j
public class AppConnContainer implements ConnContainer {
    private final Long appId;
    private final Map<Serializable, Connector> connectors = new ConcurrentHashMap<>();

    public AppConnContainer(Long appId) {
        this.appId = appId;
    }

    /**
     * @param conn
     */
    @Override
    public void addConn(Connection conn) {
        ConnectorKey connectorKey = conn.getConnectorKey();
        Connector connector = connectors.get(conn.getConnectorId());
        if (connector == null) {
            connector = new Connector(connectorKey);
        }
        connector.addConn(conn);
        addOrMergeConnector(connector);
    }

    @Override
    public Connection removeConn(ConnectorKey connectorKey, UUID connId) {
        Serializable connectorId = connectorKey.getConnectorId();
        Connector connector = connectors.get(connectorId);
        if (connector == null) {
            return null;
        }
        Connection connection = connector.removeConn(connId);
        if (connector.getConnSize() == 0) {
            // 没有其他连接时可以清除connector
            // 上锁保证没有其他线程继续添加连接或者移除connector
            connector.getRwLock().writeLock().lock();
            /*
            双重检查，在上锁之前可能有其他线程添加了新的连接，
            或者已经将connector从map中移除，则不需要再次remove
            避免移除了不该移除的connector对象
             */
            if (connector.getConnSize() == 0 && connectors.containsValue(connector)) {
                connectors.remove(connectorId);
            }
            connector.getRwLock().writeLock().unlock();
        }
        return connection;
    }

    /**
     * 将新的connector安全地添加到map中
     * @param connector
     */
    private void addOrMergeConnector(Connector connector) {
        Serializable connectorId = connector.getConnectorKey().getConnectorId();
        // 加锁保证connector添加到map中后不会又被移除
        connector.getRwLock().readLock().lock();
        // 将上锁的connector放到map中，就不用担心这个connector被移除
        // 因此也可以放心地将旧的connector的连接添加到新连接中
        Connector preConnector = connectors.put(connectorId, connector);
        if (preConnector != null) {
            for (Connection c : preConnector.getConns().values()) {
                connector.addConn(c);
            }
        }
        connector.getRwLock().readLock().unlock();
    }

    @Override
    public Connection getConn(ConnectorKey connectorKey, UUID connId) {
        Connector connector = connectors.get(connectorKey.getConnectorId());
        if (connector == null) {
            return null;
        }
        return connector.getConn(connId);
    }

    @Override
    public Map<UUID, Connection> getConns(ConnectorKey connectorKey) {
        Connector connector = connectors.get(connectorKey.getConnectorId());
        return connector.getConns();
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
