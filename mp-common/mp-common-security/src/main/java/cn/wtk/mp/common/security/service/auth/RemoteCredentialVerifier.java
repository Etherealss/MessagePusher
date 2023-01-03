package cn.wtk.mp.common.security.service.auth;

import lombok.RequiredArgsConstructor;

/**
 * @author wtk
 * @date 2022-10-08
 */
@RequiredArgsConstructor
public class RemoteCredentialVerifier implements ICredentialVerifier {

    private final CredentialCacheHandler credentialCacheHandler;

    @Override
    public <T extends Credential> T verify(String token, Class<T> credentialType){
        return credentialCacheHandler.verifyAndGet(token, credentialType);
    }

}
