package cn.wtk.mp.auth.infrastructure.token;


import cn.wtk.mp.auth.infrastructure.config.ICredentialCacheConfig;
import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.utils.UUIDUtil;
import cn.wtk.mp.common.security.exception.TokenException;
import cn.wtk.mp.common.security.service.auth.Credential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @author wtk
 * @date 2022-08-30
 */
@SuppressWarnings("unchecked")
@Component
@Slf4j
@RequiredArgsConstructor
public class RedisTokenHandlerImpl implements ITokenHandler {

    private final RedisTemplate<String, Credential> redisTemplate;

    @Override
    public void createToken(Credential credential, ICredentialCacheConfig config) {
        UUID token = UUIDUtil.get();
        credential.setToken(token.toString());
        Date tokenExpireAt = new Date(config.getTokenExpireMs() + System.currentTimeMillis());
        credential.setTokenExpireAt(tokenExpireAt);

        UUID refreshToken = UUIDUtil.get();
        credential.setRefreshToken(refreshToken.toString());
        Date refreshExpireAt = new Date(config.getRefreshTokenExpireMs() + System.currentTimeMillis());
        credential.setRefreshTokenExpireAt(refreshExpireAt);

        String redisKey = tokenKey(credential.getToken(), config);
        redisTemplate.opsForValue().set(redisKey, credential);
        redisTemplate.expireAt(redisKey, tokenExpireAt);

        redisKey = refreshTokenKey(credential.getRefreshToken(), config);
        redisTemplate.opsForValue().set(redisKey, credential);
        redisTemplate.expireAt(redisKey, refreshExpireAt);
    }

    @Override
    public <T extends Credential> T verifyToken(String token, Class<T> credentialType, ICredentialCacheConfig config) {
        Credential credential = redisTemplate.opsForValue().get(tokenKey(token, config));
        if (credential == null || credential.getClass() != credentialType) {
            throw new TokenException(ApiInfo.USER_TOKEN_INVALID);
        }
        return (T) credential;
    }

    @Override
    public <T extends Credential> T verifyRefreshToken(String refreshToken, Class<T> credentialType, ICredentialCacheConfig config) {
        String key = refreshTokenKey(refreshToken, config);
        Credential credential = redisTemplate.opsForValue().get(key);
        if (credential == null || credential.getClass() != credentialType) {
            throw new TokenException(ApiInfo.USER_TOKEN_INVALID);
        }
        return (T) credential;
    }

    @Override
    public <T extends Credential> T refreshToken(String refreshToken, Class<T> credentialType, ICredentialCacheConfig config) {
        String key = refreshTokenKey(refreshToken, config);
        Credential credential = this.verifyRefreshToken(refreshToken, credentialType, config);
        redisTemplate.delete(tokenKey(credential.getToken(), config));
        redisTemplate.delete(refreshTokenKey(credential.getRefreshToken(), config));
        createToken(credential, config);
        return (T) credential;
    }

    @Override
    public <T> void invalidateToken(String token, Class<T> credentialType, ICredentialCacheConfig config) {
        // 来到这里，可以认为 token 有效
        String tokenKey = tokenKey(token, config);
        Credential credential = redisTemplate.opsForValue().getAndDelete(tokenKey);
        if (credential != null && StringUtils.hasText(credential.getRefreshToken())) {
            String refreshTokenKey = refreshTokenKey(credential.getRefreshToken(), config);
            redisTemplate.opsForValue().getAndDelete(refreshTokenKey);
        }
    }

    private String tokenKey(String token, ICredentialCacheConfig config) {
        return config.getTokenCacheKey() + ":" + token;
    }

    private String refreshTokenKey(String refreshToken, ICredentialCacheConfig config) {
        return config.getRefreshTokenCacheKey() + ":" + refreshToken;
    }
}
