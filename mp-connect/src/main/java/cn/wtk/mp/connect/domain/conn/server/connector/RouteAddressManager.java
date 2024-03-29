package cn.wtk.mp.connect.domain.conn.server.connector;

import cn.wtk.mp.common.base.exception.service.NotFoundException;
import cn.wtk.mp.connect.infrastructure.client.dto.ConnectorAddressDTO;
import cn.wtk.mp.connect.infrastructure.config.NettyServerConfig;
import cn.wtk.mp.connect.infrastructure.event.ConnectorCreatedEvent;
import cn.wtk.mp.connect.infrastructure.event.ConnectorRemovedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 与其他连接服务交互
 * @author wtk
 * @date 2023-01-20
 */
@Component
@Slf4j
public class RouteAddressManager {
    private final RedisTemplate<String, String> redisTemplate;
    private final NettyServerConfig config;
    private final String mappingKeyPrefix;

    public RouteAddressManager(RedisTemplate<String, String> redisTemplate,
                               NettyServerConfig config,
                               @Value("${mp.redis-key.connector.server-mapping}")
                               String mappingKeyPrefix) {
        this.redisTemplate = redisTemplate;
        this.config = config;
        this.mappingKeyPrefix = mappingKeyPrefix;
    }

    @EventListener(ConnectorCreatedEvent.class)
    public void handleConnectorCreated(ConnectorCreatedEvent event) {
        Long connectorId = event.getConnectorId();
        log.debug("新增连接者：{} 的路由信息", connectorId);
        String redisKey = mappingKeyPrefix + ":" + connectorId.toString();
        String address = config.getIp() + ":" + config.getPort();
        redisTemplate.opsForValue().set(redisKey, address);
    }

    public ConnectorAddressDTO getConnectorRouteAddress(Long connectorId) {
        String redisKey = mappingKeyPrefix + ":" + connectorId;
        String address = redisTemplate.opsForValue().get(redisKey);
        if (!StringUtils.hasText(address)) {
            throw new NotFoundException("connector: " + connectorId + " 未连接到服务器，没有路由地址信息");
        }
        String[] info = address.split(":");
        ConnectorAddressDTO dto = new ConnectorAddressDTO();
        dto.setIp(info[0]);
        dto.setPort(Integer.parseInt(info[1]));
        dto.setConnectorId(connectorId);
        return dto;
    }

    public List<ConnectorAddressDTO> getConnectorRouteAddresses(List<Long> connectorIds) {
        List<String> keys = connectorIds.stream()
                .map(id -> mappingKeyPrefix + ":" + id)
                .collect(Collectors.toList());
        List<String> addresses = redisTemplate.opsForValue().multiGet(keys);
        if (CollectionUtils.isEmpty(addresses)) {
            throw new RuntimeException("获取路由信息时返回链表为空");
        }
        int size = connectorIds.size();
        List<ConnectorAddressDTO> dtos = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            String address = addresses.get(i);
            if (StringUtils.hasText(address)) {
                String[] info = address.split(":");
                ConnectorAddressDTO dto = new ConnectorAddressDTO();
                dto.setIp(info[0]);
                dto.setPort(Integer.parseInt(info[1]));
                dto.setConnectorId(connectorIds.get(i));
                dtos.add(dto);
            } else {
                ConnectorAddressDTO dto = new ConnectorAddressDTO();
                dto.setConnectorId(connectorIds.get(i));
                dtos.add(dto);
            }
        }
        return dtos;
    }

    @EventListener(ConnectorRemovedEvent.class)
    public void handleConnectorRemove(ConnectorRemovedEvent event) {
        Long connectorId = event.getConnectorId();
        log.debug("删除连接者：{} 的路由信息", connectorId);
        String redisKey = mappingKeyPrefix + ":" + connectorId.toString();
        redisTemplate.opsForValue().getAndDelete(redisKey);
        // TODO MQ 消息关闭，清内存
    }
}
