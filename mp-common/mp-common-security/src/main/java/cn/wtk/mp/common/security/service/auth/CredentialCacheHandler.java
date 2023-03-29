package cn.wtk.mp.common.security.service.auth;


import cn.wtk.mp.common.base.exception.service.ServiceFiegnException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;

/**
 * Credential 本地缓存，如果缓存查不到会自动查远程服务
 * @author wtk
 * @date 2022-10-09
 */
@SuppressWarnings("unchecked")
@RequiredArgsConstructor
@Slf4j
public abstract class CredentialCacheHandler {

    private final RedisTemplate<String, TokenCredential> redisTemplate;

    /**
     * 获取在 Redis 上缓存 Credential 的 key
     * @param token
     * @return
     */
    protected abstract String getCacheKey(String token);

    protected abstract TokenCredential verifyAndGetCredential(String token) throws ServiceFiegnException;

    public <T extends TokenCredential> T verifyAndGet(String token) throws ServiceFiegnException {
        String key = this.getCacheKey(token);
        TokenCredential credential = getByCache(key);
        if (credential != null) {
            return (T) credential;
        }
        credential = getByRemote(token, key);
        return (T) credential;
    }

    private <T extends TokenCredential> TokenCredential getByRemote(String token, String key) {
        TokenCredential credential = this.verifyAndGetCredential(token);
        cacheCredential(key, credential, credential.getExpireAt());
        return credential;
    }

    private TokenCredential getByCache(String key) {
        log.trace("获取在 Redis 上缓存 Credential 的 key");
        TokenCredential credential = redisTemplate.opsForValue().get(key);
        return credential;
    }

    private void cacheCredential(String key, TokenCredential value, Date expireAt) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expireAt(key, expireAt);
    }

    public <T> void invalidCache(String token) {
        String key = this.getCacheKey(token);
        redisTemplate.opsForValue().getAndDelete(key);
    }
}
