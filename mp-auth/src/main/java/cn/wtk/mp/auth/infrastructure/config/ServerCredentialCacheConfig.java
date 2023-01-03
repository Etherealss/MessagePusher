package cn.wtk.mp.auth.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 认证服务创建的 serverToken 的缓存相关配置
 * @author wtk
 * @date 2022-08-30
 */
@Configuration
@Getter
@Setter
public class ServerCredentialCacheConfig implements ICredentialCacheConfig {

    @Value("${mp.auth.server.token.expire-ms}")
    private Long tokenExpireMs;
    @Value("${mp.auth.server.token.cache-key}")
    private String tokenCacheKey;
    
    @Value("${mp.auth.server.refresh-token.expire-ms}")
    private Long refreshTokenExpireMs;
    @Value("${mp.auth.server.refresh-token.cache-key}")
    private String refreshTokenCacheKey;


}
