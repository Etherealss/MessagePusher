package cn.wtk.mp.common.security.service.auth;

import cn.wtk.mp.common.security.service.auth.server.ServerTokenCredential;
import cn.wtk.mp.common.security.service.auth.server.ServerCredentialCacheHandler;
import lombok.RequiredArgsConstructor;

/**
 * @author wtk
 * @date 2022-10-08
 */
@RequiredArgsConstructor
public class RemoteServerCredentialVerifier implements ICredentialVerifier<ServerTokenCredential> {

    private final ServerCredentialCacheHandler credentialCacheHandler;

    @Override
    public ServerTokenCredential verify(String token){
        return credentialCacheHandler.verifyAndGet(token);
    }
}
