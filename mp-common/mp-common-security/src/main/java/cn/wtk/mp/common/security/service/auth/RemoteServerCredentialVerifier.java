package cn.wtk.mp.common.security.service.auth;

import cn.wtk.mp.common.security.service.auth.server.ServerCredentialCacheHandler;
import cn.wtk.mp.common.security.service.auth.server.ServerTokenCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wtk
 * @date 2022-10-08
 */
@RequiredArgsConstructor
@Slf4j
public class RemoteServerCredentialVerifier implements ICredentialVerifier<ServerTokenCredential> {

    private final ServerCredentialCacheHandler credentialCacheHandler;

    @Override
    public ServerTokenCredential verify(String token) {
        log.debug("调用Auth服务验证服务Token：{}", token);
        return credentialCacheHandler.verifyAndGet(token);
    }
}
