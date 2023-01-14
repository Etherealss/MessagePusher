package cn.wtk.mp.auth.application;

import cn.wtk.mp.auth.domain.auth.server.credential.ServerCredentialService;
import cn.wtk.mp.auth.domain.auth.server.info.ServerInfoEntity;
import cn.wtk.mp.auth.domain.auth.server.info.ServerInfoService;
import cn.wtk.mp.common.security.service.auth.server.ServerAuthCommand;
import cn.wtk.mp.common.security.service.auth.server.ServerCredential;
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
public class ServerApplicationService {

    private final ServerInfoService serviceInfoService;
    private final ServerCredentialService serverCredentialService;

    public ServerCredential createServerCredential(ServerAuthCommand command) {
        ServerInfoEntity serverInfoEntity = serviceInfoService.verifyAndGetInfo(
                command
        );
        return serverCredentialService.create(serverInfoEntity);
    }
}
