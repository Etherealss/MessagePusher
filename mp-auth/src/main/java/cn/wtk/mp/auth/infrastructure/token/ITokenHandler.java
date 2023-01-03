package cn.wtk.mp.auth.infrastructure.token;


import cn.wtk.mp.auth.infrastructure.config.ICredentialCacheConfig;
import cn.wtk.mp.common.security.service.auth.Credential;

/**
 * @author wtk
 * @date 2022-08-30
 */
public interface ITokenHandler {

    void createToken(Credential credential, ICredentialCacheConfig config);

    // TODO updateUserCredential 在用户权限更新时修改token权限缓存

    <T extends Credential> T verifyToken(String token, Class<T> credentialType, ICredentialCacheConfig config);

    <T extends Credential> T verifyRefreshToken(String refreshToken, Class<T> credentialType, ICredentialCacheConfig config);

    <T extends Credential> T refreshToken(String refreshToken, Class<T> credentialType, ICredentialCacheConfig config);

    /**
     * 使 token 失效
     * @param token
     * @param credentialType
     * @param config
     * @param <T>
     */
    <T> void invalidateToken(String token, Class<T> credentialType, ICredentialCacheConfig config);
}
