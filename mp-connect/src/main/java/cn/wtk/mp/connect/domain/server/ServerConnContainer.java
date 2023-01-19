package cn.wtk.mp.connect.domain.server;

import cn.wtk.mp.connect.domain.server.app.AppConnContainer;
import cn.wtk.mp.connect.domain.server.app.connector.ConnectorKey;
import cn.wtk.mp.connect.domain.server.app.connector.connection.Connection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wtk
 * @date 2023-01-06
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ServerConnContainer implements ConnContainer {

    private final Map<Long, AppConnContainer> apps = new ConcurrentHashMap<>();
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void addConn(Connection conn) {
        Long appId = conn.getConnectorKey().getAppId();
        AppConnContainer appConnContainer = apps.get(appId);
        if (appConnContainer == null) {
            appConnContainer = new AppConnContainer(appId);
        }
        appConnContainer.addConn(conn);
        addOrMergeContainer(appConnContainer);
    }

    @Override
    public Connection removeConn(ConnectorKey connectorKey, UUID connId) {
        Long appId = connectorKey.getAppId();
        AppConnContainer container = apps.get(appId);
        if (container == null) {
            return null;
        }
        Connection conn = container.removeConn(connectorKey, connId);
        if (container.getConnectorSize() == 0) {
            container.getRwLock().writeLock().lock();
            if (container.getConnectorSize() == 0 && apps.containsValue(container)) {
                apps.remove(container);
            }
            container.getRwLock().readLock().unlock();
        }
        return conn;
    }

    private void addOrMergeContainer(AppConnContainer container) {
        Long appId = container.getAppId();
        container.getRwLock().readLock().lock();
        AppConnContainer preContainer = apps.put(appId, container);
        if (preContainer != null) {
            container.addAll(preContainer);
        }
        container.getRwLock().readLock().unlock();
    }

    @Override
    public Connection getConn(ConnectorKey connectorKey, UUID connId) {
        AppConnContainer appConnContainer = this.apps.get(connectorKey.getAppId());
        return appConnContainer.getConn(connectorKey, connId);
    }
}
