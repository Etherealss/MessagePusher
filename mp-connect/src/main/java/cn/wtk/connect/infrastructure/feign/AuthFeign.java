package cn.wtk.connect.infrastructure.feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author wtk
 * @date 2023-01-09
 */
@FeignClient(
        value = "auth",
        path = "/auth/"
)
public interface AuthFeign {
}
