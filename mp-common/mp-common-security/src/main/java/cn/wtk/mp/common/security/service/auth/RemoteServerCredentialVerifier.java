package cn.wtk.mp.common.security.service.auth;

import cn.wtk.mp.common.security.service.auth.server.ServerCredential;
import cn.wtk.mp.common.security.service.auth.server.ServerCredentialCacheHandler;
import lombok.RequiredArgsConstructor;

/**
 * @author wtk
 * @date 2022-10-08
 */
@RequiredArgsConstructor
public class RemoteServerCredentialVerifier implements ICredentialVerifier<ServerCredential> {

    private final ServerCredentialCacheHandler credentialCacheHandler;

    @Override
    public ServerCredential verify(String token){
        return credentialCacheHandler.verifyAndGet(token);
    }
}
