package cn.wtk.mp.auth.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 认证服务创建的 userToken 的缓存相关配置
 * @author wtk
 * @date 2022-08-30
 */
@Configuration
@Validated
@Getter
@Setter
public class DisposableCredentialCacheConfig implements ICredentialCacheConfig {
    @Value("${mp.auth.disposable.token.expire-ms}")
    @NotNull
    private Long tokenExpireMs;
    @Value("${mp.auth.disposable.token.cache-key}")
    @NotBlank
    private String tokenCacheKey;

    @Value("${mp.auth.disposable.refresh-token.expire-ms}")
    @NotNull
    private Long refreshTokenExpireMs;
    @Value("${mp.auth.disposable.refresh-token.cache-key}")
    @NotBlank private String refreshTokenCacheKey;
}
