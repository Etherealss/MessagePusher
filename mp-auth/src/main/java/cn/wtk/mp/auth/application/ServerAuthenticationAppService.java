package cn.wtk.mp.auth.application;

import cn.wtk.mp.auth.domain.auth.server.info.ServerInfoEntity;
import cn.wtk.mp.auth.domain.auth.server.info.ServerInfoService;
import cn.wtk.mp.auth.domain.credential.TokenCredentialService;
import cn.wtk.mp.auth.domain.credential.TokenCredentialSpec;
import cn.wtk.mp.auth.infrastructure.config.ServerCredentialConfig;
import cn.wtk.mp.common.base.exception.rest.ParamErrorException;
import cn.wtk.mp.common.security.service.auth.server.ServerAuthCommand;
import cn.wtk.mp.common.security.service.auth.server.ServerTokenCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @date 2022-12-29
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ServerAuthenticationAppService {

    private final ServerInfoService serviceInfoService;
    private final TokenCredentialService tokenCredentialService;
    private final ServerCredentialConfig serverCredentialConfig;

    public ServerTokenCredential createServerCredential(ServerAuthCommand command) {
        // 通过密钥获取 serverInfo，再拿去创建 token
        ServerInfoEntity serverInfoEntity = serviceInfoService.verifyAndGetInfo(
                command
        );
        TokenCredentialSpec tokenCredentialSpec = new TokenCredentialSpec(
                serverCredentialConfig.getTokenTopic(),
                serverCredentialConfig.getExpireMs()
        );
        ServerTokenCredential credential = new ServerTokenCredential();
        credential.setServerId(serverInfoEntity.getId());
        credential.setServerName(serverInfoEntity.getServerName());
        tokenCredentialService.completeAndSave(credential, tokenCredentialSpec);
        return credential;
    }

    public ServerTokenCredential verifyAndGetToken(String token) {
        ServerTokenCredential tokenCredential = tokenCredentialService.verifyAndGet(token, ServerTokenCredential.class);
        if (!serverCredentialConfig.getTokenTopic().equals(tokenCredential.getTokenTopic())) {
            throw new ParamErrorException("token topic 不匹配");
        }
        return tokenCredential;
    }
}
