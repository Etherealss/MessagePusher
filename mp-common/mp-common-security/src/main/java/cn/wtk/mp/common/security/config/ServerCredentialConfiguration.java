package cn.wtk.mp.common.security.config;


import cn.wtk.mp.common.security.feign.ServerCredentialFeign;
import cn.wtk.mp.common.security.service.auth.CredentialCacheHandler;
import cn.wtk.mp.common.security.service.auth.ICredentialVerifier;
import cn.wtk.mp.common.security.service.auth.RemoteCredentialVerifier;
import cn.wtk.mp.common.security.service.auth.server.IServerCredentialProvider;
import cn.wtk.mp.common.security.service.auth.server.RemoteIServerCredentialProvider;
import cn.wtk.mp.common.security.service.auth.server.ServerCredential;
import cn.wtk.mp.common.security.service.auth.server.ServerDigestGenerator;
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
            RedisTemplate<String, ServerCredential> redisTemplate,
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
     * @param userTokenFeign
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ICredentialVerifier remoteTokenVerifier(@Lazy CredentialCacheHandler userTokenFeign) {
        log.info("非 auth 服务，使用 RemoteTokenVerifier 验证 Token");
        return new RemoteCredentialVerifier(userTokenFeign);
    }
}
