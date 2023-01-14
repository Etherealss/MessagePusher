package cn.wtk.connect.infrastructure.feign;

import cn.wtk.mp.common.security.service.auth.disposal.DisposableCredential;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

/**
 * @author wtk
 * @date 2023-01-09
 */
@FeignClient(
        value = "mp-auth",
        path = "/auth"
)
public interface AuthFeign {
    @GetMapping("/apps/consumables/{key}/credentials/{token}")
    DisposableCredential verify(@PathVariable Serializable key,
                                @PathVariable String token);
}
