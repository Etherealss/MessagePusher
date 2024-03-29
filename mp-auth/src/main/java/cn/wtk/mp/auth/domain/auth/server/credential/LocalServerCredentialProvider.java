package cn.wtk.mp.auth.domain.auth.server.credential;


import cn.wtk.mp.auth.application.ServerAuthenticationAppService;
import cn.wtk.mp.common.security.config.ServerCredentialConfig;
import cn.wtk.mp.common.security.service.auth.server.IServerCredentialProvider;
import cn.wtk.mp.common.security.service.auth.server.ServerAuthCommand;
import cn.wtk.mp.common.security.service.auth.server.ServerDigestGenerator;
import cn.wtk.mp.common.security.service.auth.server.ServerTokenCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2022-10-17
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class LocalServerCredentialProvider implements IServerCredentialProvider {
    private final ServerCredentialConfig serverCredentialConfig;
    private final ServerAuthenticationAppService serverAuthenticationAppService;
    private final ServerDigestGenerator serverDigestGenerator;

    @Override
    public ServerTokenCredential create() {
        byte[] digest = serverDigestGenerator.generate(
                serverCredentialConfig.getServerId(),
                serverCredentialConfig.getServerName(),
                serverCredentialConfig.getSecret()
        );
        ServerAuthCommand command = new ServerAuthCommand(
                serverCredentialConfig.getServerId(), new String(digest)
        );
        Long serverId = serverCredentialConfig.getServerId();
        return serverAuthenticationAppService.createServerCredential(command);
    }
}
