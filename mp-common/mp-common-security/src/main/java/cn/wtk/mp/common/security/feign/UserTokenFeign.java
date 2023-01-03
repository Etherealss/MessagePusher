package cn.wtk.mp.common.security.feign;

import cn.wtk.mp.common.security.service.auth.user.UserCredential;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author wtk
 * @date 2022-10-08
 */
@FeignClient(
        value = "nowander-auth",
        contextId = "nowander-auth-users-tokens",
        path = "/auth/users"
)
public interface UserTokenFeign {

    @GetMapping("/credentials/{token}")
    UserCredential verify(@PathVariable String token);
}
