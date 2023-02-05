package cn.wtk.mp.common.security.service.auth.server;

import cn.wtk.mp.common.security.config.ServerCredentialConfig;
import cn.wtk.mp.common.security.feign.ServerCredentialFeign;
import cn.wtk.mp.common.security.service.auth.TokenCredential;
import cn.wtk.mp.common.security.service.auth.CredentialCacheHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 针对服务端 ServerCredential 的本地缓存
 * @author wtk
 * @date 2023-01-04
 */
@Slf4j
@Component
public class ServerCredentialCacheHandler extends CredentialCacheHandler {

    private final ServerCredentialConfig serverCredentialConfig;
    private final ServerCredentialFeign serverCredentialFeign;

    public ServerCredentialCacheHandler(RedisTemplate<String, TokenCredential> redisTemplate, ServerCredentialConfig serverCredentialConfig, ServerCredentialFeign serverCredentialFeign) {
        super(redisTemplate);
        this.serverCredentialConfig = serverCredentialConfig;
        this.serverCredentialFeign = serverCredentialFeign;
    }

    @Override
    protected String getCacheKey(String token) {
        return serverCredentialConfig.getCacheKey() + ":" + token;
    }

    @Override
    protected TokenCredential verifyAndGetCredential(String token) {
        return serverCredentialFeign.verify(token);
    }
}
