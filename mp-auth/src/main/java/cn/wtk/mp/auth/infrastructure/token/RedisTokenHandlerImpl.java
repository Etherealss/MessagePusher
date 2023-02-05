package cn.wtk.mp.auth.infrastructure.token;


import cn.wtk.mp.auth.infrastructure.config.TokenCredentialConfig;
import cn.wtk.mp.common.security.service.auth.TokenCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisTokenHandlerImpl implements ITokenHandler {

    private final RedisTemplate<String, TokenCredential> redisTemplate;
    private final TokenCredentialConfig config;

    @Override
    public void saveToken(TokenCredential credential) {
        Objects.requireNonNull(credential.getExpireAt());
        String redisKey = tokenKey(credential.getToken());
        redisTemplate.opsForValue().set(redisKey, credential);
        redisTemplate.expireAt(redisKey, credential.getExpireAt());
    }

    @Override
    public TokenCredential getToken(String token) {
        return redisTemplate.opsForValue().get(tokenKey(token));
    }

    @Override
    public TokenCredential getAndDeleteToken(String token) {
        String tokenKey = tokenKey(token);
        return redisTemplate.opsForValue().getAndDelete(tokenKey(token));
    }

    @Override
    public TokenCredential deleteToken(String token) {
        String tokenKey = tokenKey(token);
        return redisTemplate.opsForValue().getAndDelete(tokenKey);
    }

    private String tokenKey(String token) {
        return config.getCacheKey() + ":" + token;
    }
}
