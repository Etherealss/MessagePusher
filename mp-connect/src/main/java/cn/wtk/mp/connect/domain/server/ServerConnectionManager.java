package cn.wtk.mp.connect.domain.server;

import cn.wtk.mp.connect.domain.server.app.AppConnManager;
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
public class ServerConnectionManager implements ConnComposite {

    private final Map<Long, AppConnManager> apps = new ConcurrentHashMap<>();
    private final RedisTemplate<String, String> redisTemplate;

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
