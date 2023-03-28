package cn.wtk.mp.auth.domain.auth.server.credential;


import cn.wtk.mp.auth.domain.credential.TokenCredentialService;
import cn.wtk.mp.auth.infrastructure.config.ServerCredentialAuthConfig;
import cn.wtk.mp.common.security.service.auth.ICredentialVerifier;
import cn.wtk.mp.common.security.service.auth.RemoteServerCredentialVerifier;
import cn.wtk.mp.common.security.service.auth.server.ServerTokenCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 取代 RemoteTokenVerifier，直接调用本地代码验证token
 * @author wtk
 * @date 2022-10-08
 * @see RemoteServerCredentialVerifier
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class LocalServerCredentialVerifier implements ICredentialVerifier<ServerTokenCredential> {
    private final TokenCredentialService tokenCredentialService;
    private final ServerCredentialAuthConfig serverCredentialAuthConfig;

    @PostConstruct
    public void postConstruct() {
        log.info("Auth 服务，采用进程内调用的方式验证ServerToken");
    }

    @Override
    public ServerTokenCredential verify(String token) {
        return tokenCredentialService.verifyAndGet(token, ServerTokenCredential.class);
    }
}
