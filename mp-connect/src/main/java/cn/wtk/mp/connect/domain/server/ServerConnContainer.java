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
            apps.get(appId).addConn(conn);
            apps.putIfAbsent(appId, appConnContainer);
        } else {
            appConnContainer.addConn(conn);
        }
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
}
