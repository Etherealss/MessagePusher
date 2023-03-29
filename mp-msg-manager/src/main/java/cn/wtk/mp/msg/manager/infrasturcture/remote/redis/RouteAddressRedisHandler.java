package cn.wtk.mp.msg.manager.infrasturcture.remote.redis;

import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.connect.ConnectorAddressDTO;
import cn.wtk.mp.msg.manager.infrasturcture.remote.feign.ConnectFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023/2/24
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class RouteAddressRedisHandler {
    private ConnectFeign connectFeign;

    /**
     * TODO 缓存时间、缓存失效
     * @param connectorId
     * @return
     */
    @Cacheable(cacheNames = "${mp.manager.route.address.cache-key}", key="#connectorId")
    public ConnectorAddressDTO getRouteAddress(Long connectorId) {
        return connectFeign.getConnectorAddress(connectorId);
    }
}
