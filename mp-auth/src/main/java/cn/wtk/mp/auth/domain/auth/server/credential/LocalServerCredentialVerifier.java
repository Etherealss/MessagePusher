package cn.wtk.mp.auth.domain.auth.server.credential;


import cn.wtk.mp.auth.domain.credential.TokenCredentialService;
import cn.wtk.mp.auth.infrastructure.config.ServerCredentialAuthConfig;
import cn.wtk.mp.common.security.service.auth.ICredentialVerifier;
import cn.wtk.mp.common.security.service.auth.RemoteServerCredentialVerifier;
import cn.wtk.mp.common.security.service.auth.server.ServerTokenCredential;
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
public class LocalServerCredentialVerifier implements ICredentialVerifier<ServerTokenCredential> {
    private final TokenCredentialService tokenCredentialService;
    private final ServerCredentialAuthConfig serverCredentialAuthConfig;

    @Override
    public ServerTokenCredential verify(String token) {
        return tokenCredentialService.verifyAndGet(token, ServerTokenCredential.class);
    }
}
