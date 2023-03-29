package cn.wtk.mp.client.infrastructure.remote.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author wtk
 * @date 2023/3/29
 */
@FeignClient(
        value = "mp-connect",
        path = "/connect"
)
public interface ConnectFeign {

}
