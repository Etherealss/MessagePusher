package cn.wtk.mp.auth.infrastructure.token;


import cn.wtk.mp.auth.application.ServerApplicationService;
import cn.wtk.mp.common.security.config.ServerCredentialConfig;
import cn.wtk.mp.common.security.service.auth.server.IServerCredentialProvider;
import cn.wtk.mp.common.security.service.auth.server.ServerAuthCommand;
import cn.wtk.mp.common.security.service.auth.server.ServerCredential;
import cn.wtk.mp.common.security.service.auth.server.ServerDigestGenerator;
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
    private final ServerApplicationService serverApplicationService;
    private final ServerDigestGenerator serverDigestGenerator;

    @Override
    public ServerCredential create() {
        byte[] digest = serverDigestGenerator.generate(
                serverCredentialConfig.getServerId(),
                serverCredentialConfig.getServerName(),
                serverCredentialConfig.getSecret()
        );
        ServerAuthCommand command = new ServerAuthCommand(serverCredentialConfig.getServerId(), digest);
        Long serverId = serverCredentialConfig.getServerId();
        return serverApplicationService.createServerCredential(command);
    }
}
