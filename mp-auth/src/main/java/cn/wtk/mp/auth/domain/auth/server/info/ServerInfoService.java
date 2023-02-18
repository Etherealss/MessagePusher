package cn.wtk.mp.auth.domain.auth.server.info;


import cn.wtk.mp.auth.infrastructure.client.command.RegisterServerCommand;
import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.service.AuthenticationException;
import cn.wtk.mp.common.base.exception.service.NotFoundException;
import cn.wtk.mp.common.security.service.auth.server.ServerAuthCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ServerInfoService {
    private final PasswordEncoder passwordEncoder;
    private final ServerInfoRepository serverInfoRepository;
    private final ServerDigestValidator serverDigestValidator;

    @Transactional(rollbackFor = Exception.class)
    public ServerInfoEntity verifyAndGetInfo(ServerAuthCommand command) {
        Long serverId = command.getServerId();
        ServerInfoEntity serverInfoEntity = serverInfoRepository.findById(serverId)
                .orElseThrow(() -> new NotFoundException("服务：" + serverId + "不存在"));
        if (!serverDigestValidator.validate(command.getUserInputDigest(), serverInfoEntity)) {
            throw new AuthenticationException(ApiInfo.PASSWORD_ERROR, "服务秘钥错误");
        }
        return serverInfoEntity;
    }

    @Transactional(rollbackFor = Exception.class)
    public Long createAuthInfo(RegisterServerCommand command) {
        ServerInfoEntity entity = new ServerInfoEntity(
                command.getServerName(),
                command.getSecret()
        );
        serverInfoRepository.insert(entity);
        return entity.getId();
    }
}
