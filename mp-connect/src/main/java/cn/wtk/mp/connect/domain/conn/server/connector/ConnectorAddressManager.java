package cn.wtk.mp.connect.domain.conn.server.connector;

import cn.wtk.mp.common.base.exception.service.NotFoundException;
import cn.wtk.mp.common.base.lock.CacheLock;
import cn.wtk.mp.common.base.lock.RedisLockHelper;
import cn.wtk.mp.common.base.lock.RedisLockOperator;
import cn.wtk.mp.common.security.service.auth.connector.ConnectorCredential;
import cn.wtk.mp.connect.infrastructure.client.dto.ConnectorAddressDTO;
import cn.wtk.mp.connect.infrastructure.config.ConnectorAddressLockProperties;
import cn.wtk.mp.connect.infrastructure.config.NettyServerConfig;
import cn.wtk.mp.connect.infrastructure.event.ConnectorCreatedEvent;
import cn.wtk.mp.connect.infrastructure.event.ConnectorRemovedEvent;
import cn.wtk.mp.connect.infrastructure.remote.feign.AuthFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 与其他连接服务交互
 * @author wtk
 * @date 2023-01-20
 */
@Component
@Slf4j
public class ConnectorAddressManager {
    private final RedisTemplate<String, String> redisTemplate;
    private final NettyServerConfig config;
    private final ConnectorAddressLockProperties properties;
    private final RedisLockHelper connectRedisLockHelper;
    private final String connectorMappingKeyPrefix;
    private final String connectAddressKeyPrefix;
    private final AuthFeign authFeign;

    public ConnectorAddressManager(RedisTemplate<String, String> redisTemplate,
                                   NettyServerConfig config,
                                   RedisLockOperator redisLockOperator,
                                   ConnectorAddressLockProperties properties,
                                   @Value("${mp.redis-key.connector.server-mapping}")
                                   String connectorMappingKeyPrefix,
                                   @Value("${mp.redis-key.connector.pre-server-mapping}")
                                   String connectAddressKeyPrefix, AuthFeign authFeign) {
        this.redisTemplate = redisTemplate;
        this.config = config;
        this.connectorMappingKeyPrefix = connectorMappingKeyPrefix;
        this.connectAddressKeyPrefix = connectAddressKeyPrefix;
        this.properties = properties;
        this.authFeign = authFeign;
        this.connectRedisLockHelper = new RedisLockHelper(
                redisLockOperator,
                properties.getTotalRetryTimes(),
                properties.getRetryIntervalMs(),
                properties.getLockFailedThrowException()
        );
    }

    @CacheLock
    public ConnectorAddressDTO getAddress4Connect(Long appId, Long connectorId, String userToken) {
        log.debug("连接者：{} 请求获取路由信息以进行连接操作", connectorId);
        ConnectorCredential connectorCredential = authFeign.verify(appId, connectorId, userToken);
        String lockFlag = UUID.randomUUID().toString();
        String lockKey = properties.getLockKeyPrefix() + ":" + connectorId;
        try {
            long timeout = connectorCredential.getExpireAt().getTime() - System.currentTimeMillis();
            connectRedisLockHelper.lock(
                    lockKey,
                    lockFlag,
                    timeout
            );
            ConnectorAddressDTO connectorAddress = getAddress(connectorId);
            String connectAddressKey = connectAddressKeyPrefix + ":" + connectorId.toString();
            if (connectorAddress == null) {
                String address = redisTemplate.opsForValue().get(connectAddressKey);
                if (StringUtils.hasText(address)) {
                    return new ConnectorAddressDTO(connectorId, address);
                } else {
                    String curServerAddress = config.getIp() + ":" + config.getPort();
                    redisTemplate.opsForValue().set(connectAddressKey, curServerAddress);
                    return new ConnectorAddressDTO(connectorId, config.getIp(), config.getPort());
                }
            } else {
                String address = connectorAddress.getIp() + ":" + connectorAddress.getPort();
                redisTemplate.opsForValue().set(connectAddressKey, address, timeout, TimeUnit.MILLISECONDS);
                return connectorAddress;
            }
        } catch (Throwable e) {
            connectRedisLockHelper.unlock(lockKey, lockFlag);
            throw e;
        }
    }

    @EventListener(ConnectorCreatedEvent.class)
    public void handleConnectorCreated(ConnectorCreatedEvent event) {
        Long connectorId = event.getConnectorId();
        log.debug("新增连接者：{} 的路由信息", connectorId);
        String redisKey = connectorMappingKeyPrefix + ":" + connectorId.toString();
        String address = config.getIp() + ":" + config.getPort();
        redisTemplate.opsForValue().set(redisKey, address);
    }

    public ConnectorAddressDTO getConnectorRouteAddress(Long connectorId) {
        ConnectorAddressDTO address = this.getAddress(connectorId);
        if (address == null) {
            throw new NotFoundException("connector: " + connectorId + " 未连接到服务器，没有路由地址信息");
        }
        return address;
    }

    private ConnectorAddressDTO getAddress(Long connectorId) {
        String redisKey = connectorMappingKeyPrefix + ":" + connectorId;
        String address = redisTemplate.opsForValue().get(redisKey);
        if (!StringUtils.hasText(address)) {
            return null;
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
                .map(id -> connectorMappingKeyPrefix + ":" + id)
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
        String redisKey = connectorMappingKeyPrefix + ":" + connectorId.toString();
        redisTemplate.opsForValue().getAndDelete(redisKey);
        // TODO MQ 消息关闭，清内存
    }
}
