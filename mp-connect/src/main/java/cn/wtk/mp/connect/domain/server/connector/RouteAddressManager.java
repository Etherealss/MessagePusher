package cn.wtk.mp.connect.domain.server.connector;

import cn.wtk.mp.common.base.exception.service.NotFoundException;
import cn.wtk.mp.connect.infrastructure.client.dto.ConnectorAddressDTO;
import cn.wtk.mp.connect.infrastructure.config.NettyServerConfig;
import cn.wtk.mp.connect.infrastructure.event.ConnectorCreatedEvent;
import cn.wtk.mp.connect.infrastructure.event.ConnectorRemovedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 与其他连接服务交互
 * @author wtk
 * @date 2023-01-20
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RouteAddressManager {
    private final RedisTemplate<String, String> redisTemplate;
    private final NettyServerConfig config;
    @Value("${mp.redis-key.connector.server-mapping}")
    private final String mappingKeyPrefix;

    @EventListener(ConnectorCreatedEvent.class)
    public void handleConnectorCreated(ConnectorCreatedEvent event) {
        Connector connector = event.getConnector();
        Long appId = connector.getAppId();
        Serializable connectorId = connector.getConnectorId();
        String redisKey = mappingKeyPrefix + ":"  + connectorId;
        String address = config.getIp() + ":" + config.getPort();
        redisTemplate.opsForValue().set(redisKey, address);
    }

    public ConnectorAddressDTO getConnectorRouteAddress(Long connectorId) {
        String redisKey = mappingKeyPrefix + ":"  + connectorId;
        String address = redisTemplate.opsForValue().get(redisKey);
        if (!StringUtils.hasText(address)) {
            throw new NotFoundException("connector: {} 未连接到服务器，没有路由地址信息");
        }
        String[] info = address.split(":");
        ConnectorAddressDTO dto = new ConnectorAddressDTO();
        dto.setIp(info[0]);
        dto.setPort(Integer.parseInt(info[1]));
        return dto;
    }

    @EventListener(ConnectorRemovedEvent.class)
    public void handleConnectorCreated(ConnectorRemovedEvent event) {
        Connector connector = event.getConnector();
        Long appId = connector.getAppId();
        Serializable connectorId = connector.getConnectorId();
        String redisKey = mappingKeyPrefix + ":" + connectorId;
        redisTemplate.opsForValue().getAndDelete(redisKey);
        // TODO MQ 消息关闭，清内存
    }
}
