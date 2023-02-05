package cn.wtk.mp.common.security.config;


import cn.wtk.mp.common.security.feign.ServerCredentialFeign;
import cn.wtk.mp.common.security.service.auth.ICredentialVerifier;
import cn.wtk.mp.common.security.service.auth.RemoteServerCredentialVerifier;
import cn.wtk.mp.common.security.service.auth.server.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author wtk
 * @date 2022-10-17
 */
@Configuration
@Slf4j
public class ServerCredentialConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public IServerCredentialProvider serverCredentialProvider(
            ServerCredentialFeign serverCredentialFeign,
            ServerCredentialConfig config,
            RedisTemplate<String, ServerTokenCredential> redisTemplate,
            ServerDigestGenerator serverDigestGenerator
    ) {
        log.info("非 auth 服务，使用 RemoteIServerCredentialProvider 生成 Token");
        return new RemoteIServerCredentialProvider(
                serverCredentialFeign,
                config,
                redisTemplate,
                serverDigestGenerator
        );
    }

    /**
     * WebMvcConfigurer -> Interceptor -> Feign 会导致循环依赖，需要使用 @Lazy 延迟加载
     * @param credentialCacheHandler
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ICredentialVerifier remoteTokenVerifier(@Lazy ServerCredentialCacheHandler credentialCacheHandler) {
        log.info("非 auth 服务，使用 RemoteServerCredentialVerifier 验证 Token");
        return new RemoteServerCredentialVerifier(credentialCacheHandler);
    }
}
