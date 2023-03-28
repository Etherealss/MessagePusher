package cn.wtk.mp.connect.domain.conn.server.connector;

import cn.wtk.mp.common.base.exception.service.NotFoundException;
import cn.wtk.mp.common.base.lock.CacheLock;
import cn.wtk.mp.connect.infrastructure.client.dto.ConnectorAddressDTO;
import cn.wtk.mp.connect.infrastructure.config.ConnectorAddressCacheProperties;
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
    private final ConnectorAddressCacheProperties cacheProperties;
    private final String connectorMappingKeyPrefix;
    private final String connectAddressKeyPrefix;
    private final AuthFeign authFeign;

    public ConnectorAddressManager(RedisTemplate<String, String> redisTemplate,
                                   NettyServerConfig config,
                                   ConnectorAddressCacheProperties cacheProperties,
                                   @Value("${mp.redis-key.connector.server-mapping}")
                                   String connectorMappingKeyPrefix,
                                   @Value("${mp.redis-key.connector.pre-server-mapping}")
                                   String connectAddressKeyPrefix, AuthFeign authFeign) {
        this.redisTemplate = redisTemplate;
        this.config = config;
        this.cacheProperties = cacheProperties;
        this.connectorMappingKeyPrefix = connectorMappingKeyPrefix;
        this.connectAddressKeyPrefix = connectAddressKeyPrefix;
        this.authFeign = authFeign;
    }

    @CacheLock
    public ConnectorAddressDTO getAddress4Connect(Long appId, Long connectorId, String userToken) {
        log.debug("连接者：{} 请求获取路由信息以进行连接操作", connectorId);
        String connectAddressKey = connectAddressKeyPrefix + ":" + connectorId.toString();
        redisTemplate.expire(connectAddressKey, cacheProperties.getConnectExpireMs(), TimeUnit.MILLISECONDS);
        String redisAddress = redisTemplate.opsForValue().get(connectAddressKey);
        if (StringUtils.hasText(redisAddress)) {
            return new ConnectorAddressDTO(connectorId, redisAddress);
        }
        ConnectorAddressDTO connectorAddress = getAddress(connectorId);
        String finalAddress;
        if (connectorAddress == null) {
            String curAddress = config.getIp() + ":" + config.getPort();
            finalAddress = this.cas(connectAddressKey, curAddress);
        } else {
            String routeAddress = connectorAddress.getIp() + ":" + connectorAddress.getPort();
            finalAddress = this.cas(connectAddressKey, routeAddress);
        }
        return new ConnectorAddressDTO(connectorId, finalAddress);
    }

    /**
     * @return 最终设置到Redis的连接地址
     */
    private String cas(String key, String address) {
        Boolean absent = redisTemplate.opsForValue()
                .setIfAbsent(key, address, cacheProperties.getConnectExpireMs(), TimeUnit.MILLISECONDS);
        if (Boolean.FALSE.equals(absent)) {
            address = redisTemplate.opsForValue().get(key);
            if (!StringUtils.hasText(address)) {
                throw new RuntimeException("address 不应该为空，难道是 CAS 后还是过期了？");
            }
        }
        return address;
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
        return new ConnectorAddressDTO(connectorId, address);
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
