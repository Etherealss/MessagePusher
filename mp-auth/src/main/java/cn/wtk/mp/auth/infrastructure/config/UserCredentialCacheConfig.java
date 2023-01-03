package cn.wtk.mp.auth.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 认证服务创建的 userToken 的缓存相关配置
 * @author wtk
 * @date 2022-08-30
 */
@Configuration
@Getter
@Setter
public class UserCredentialCacheConfig implements ICredentialCacheConfig {
    @Value("${app.auth.user.token.expire-ms}")
    private Long tokenExpireMs;
    @Value("${app.auth.user.token.cache-key}")
    private String tokenCacheKey;

    @Value("${app.auth.user.refresh-token.expire-ms}")
    private Long refreshTokenExpireMs;
    @Value("${app.auth.user.refresh-token.cache-key}")
    private String refreshTokenCacheKey;
}
