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
    private final Map<Serializable, Connector> connectors = new ConcurrentHashMap<>();
    @Getter
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

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
        Connector connector = connectors.get(connectorId);
        if (connector == null) {
            connector = new Connector(connectorKey);
        }
        connector.getRwLock().readLock().lock();
        connector.addConn(conn);
        log.info("connector 给自己上读锁");
        // put 操作使得已经添加的读锁的connector仍可能会被移除出map，
        // 在后续需要作出补偿操作，保证并发操作下的数据一致性
        Connector preConnector = connectors.put(connectorId, connector);
        if (!(preConnector == null || preConnector.getRwLock().equals(connector.getRwLock()))) {
            /*
             如果preConnector不为null，说明在connectors.get到connectors.put之间，
             有其他线程put了另一个connector对象，称为preConnector
             此时需要把preConnector里的连接全部添加到当前connector中
             由于前面connectors.put操作导致置换出来的preConnector可能是加了读锁的状态
             而加读锁意味着preConnector正在进行添加连接操作
             因此需要获取preConnector的读锁，
             在成功获取后将preConnector的所有连接添加到当前connector中
             1. A 被添加到 map 中
             2. B 加读锁并通过 put 添加到 map 中，A 被移出 map
             3. C 加读锁并通过 put 添加到 map 中，B 被移出 map
             4. C 对 B 加写锁，由于 B 的读锁尚未释放，C 等待
             5. B 将 A 的所有的 conn 添加到 B 中，释放读锁
             6. C 得到 B 的写锁，并将 B 的所有 conn 添加到 C 中，后释放 B 的写锁
             7. C 释放读锁，结束
             */
            preConnector.getRwLock().writeLock().lock();
            log.info("connector 请求 preConnector 写");
            connector.addAll(preConnector);
            preConnector.getRwLock().writeLock().unlock();
            log.info("connector 释放 preConnector 写");
        }
        connector.getRwLock().readLock().unlock();
        log.info("connector 释放自己的读锁");
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
