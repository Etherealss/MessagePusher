package cn.wtk.mp.auth.infrastructure.config;

/**
 * @author wtk
 * @date 2022-10-14
 */
public interface ICredentialCacheConfig {
    Long getTokenExpireMs();

    String getTokenCacheKey();

    Long getRefreshTokenExpireMs();

    String getRefreshTokenCacheKey();
}
