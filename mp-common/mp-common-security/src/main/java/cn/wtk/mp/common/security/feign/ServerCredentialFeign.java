package cn.wtk.mp.common.security.feign;


import cn.wtk.mp.common.security.service.auth.server.ServerAuthCommand;
import cn.wtk.mp.common.security.service.auth.server.ServerTokenCredential;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wtk
 * @date 2022-10-08
 */
@FeignClient(
        value = "mp-auth",
        contextId = "mp-auth-servers-tokens",
        path = "/auth/servers",
        configuration = NoServerTokenFeignConfiguration.class
)
public interface ServerCredentialFeign {

    @GetMapping("/credentials/{token}")
    ServerTokenCredential verify(@PathVariable String token);

    /**
     * 获取token
     */
    @PostMapping("/credentials")
    ServerTokenCredential createCredential(@Validated @RequestBody ServerAuthCommand command);
}
