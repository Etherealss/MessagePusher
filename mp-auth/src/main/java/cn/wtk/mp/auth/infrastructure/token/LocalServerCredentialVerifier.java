package cn.wtk.mp.auth.infrastructure.token;


import cn.wtk.mp.auth.infrastructure.config.ServerCredentialCacheConfig;
import cn.wtk.mp.common.security.service.auth.ICredentialVerifier;
import cn.wtk.mp.common.security.service.auth.RemoteServerCredentialVerifier;
import cn.wtk.mp.common.security.service.auth.server.ServerCredential;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 取代 RemoteTokenVerifier，直接调用本地代码验证token
 * @author wtk
 * @date 2022-10-08
 * @see RemoteServerCredentialVerifier
 */
@RequiredArgsConstructor
@Component
public class LocalServerCredentialVerifier implements ICredentialVerifier<ServerCredential> {
    private final ITokenHandler tokenHandler;
    private final ServerCredentialCacheConfig serverCredentialCacheConfig;

    @Override
    public ServerCredential verify(String token) {
        return tokenHandler.verifyToken(token, ServerCredential.class, serverCredentialCacheConfig);
    }
}
