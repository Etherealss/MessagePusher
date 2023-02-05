package cn.wtk.mp.connect.domain.server;

import cn.wtk.mp.connect.domain.server.connector.Connector;
import cn.wtk.mp.connect.infrastructure.config.NettyServerConfig;
import cn.wtk.mp.connect.infrastructure.event.ConnectorCreatedEvent;
import cn.wtk.mp.connect.infrastructure.event.ConnectorRemovedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 与其他连接服务交互
 * @author wtk
 * @date 2023-01-20
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ConnectorManager {
    private final RedisTemplate<String, String> redisTemplate;
    private final NettyServerConfig config;
    @Value("${mp.redis-key.connector.server-mapping}")
    private final String mappingKeyPrefix;
    private final String address = config.getIp() + ":" + config.getPort();

    @EventListener(ConnectorCreatedEvent.class)
    public void handleConnectorCreated(ConnectorCreatedEvent event) {
        Connector connector = event.getConnector();
        Long appId = connector.getAppId();
        Serializable connectorId = connector.getConnectorId();
        String redisKey = mappingKeyPrefix + ":" + appId + ":" + connectorId;
        redisTemplate.opsForValue().set(redisKey, address);
    }

    @EventListener(ConnectorRemovedEvent.class)
    public void handleConnectorCreated(ConnectorRemovedEvent event) {
        Connector connector = event.getConnector();
        Long appId = connector.getAppId();
        Serializable connectorId = connector.getConnectorId();
        String redisKey = mappingKeyPrefix + ":" + appId + ":" + connectorId;
        redisTemplate.opsForValue().getAndDelete(redisKey);
    }
}
