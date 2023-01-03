package cn.wtk.mp.auth.infrastructure.token;


import cn.wtk.mp.auth.infrastructure.config.ICredentialCacheConfig;
import cn.wtk.mp.auth.infrastructure.config.ServerCredentialCacheConfig;
import cn.wtk.mp.auth.infrastructure.config.UserCredentialCacheConfig;
import cn.wtk.mp.common.security.service.auth.Credential;
import cn.wtk.mp.common.security.service.auth.ICredentialVerifier;
import cn.wtk.mp.common.security.service.auth.server.ServerCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 取代 RemoteTokenVerifier，直接调用本地代码验证token
 * @author wtk
 * @date 2022-10-08
 * @see cn.wtk.mp.common.security.service.auth.RemoteCredentialVerifier
 */
@RequiredArgsConstructor
@Component
public class LocalCredentialVerify implements ICredentialVerifier {
    private final ITokenHandler tokenHandler;
    private final ServerCredentialCacheConfig serverCredentialCacheConfig;
    private final UserCredentialCacheConfig userCredentialCacheConfig;

    @Override
    public <T extends Credential> T verify(String token, Class<T> credentialType) {
        ICredentialCacheConfig config =
                credentialType == ServerCredential.class ? serverCredentialCacheConfig : userCredentialCacheConfig;
        return tokenHandler.verifyToken(token, credentialType, config);
    }
}
