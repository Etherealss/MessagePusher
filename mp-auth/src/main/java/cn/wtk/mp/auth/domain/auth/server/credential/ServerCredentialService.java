package cn.wtk.mp.auth.domain.auth.server.credential;

import cn.wtk.mp.auth.domain.auth.server.info.ServerInfoEntity;
import cn.wtk.mp.auth.infrastructure.config.ServerCredentialCacheConfig;
import cn.wtk.mp.auth.infrastructure.token.ITokenHandler;
import cn.wtk.mp.common.base.exception.rest.ParamErrorException;
import cn.wtk.mp.common.security.service.auth.server.ServerCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @date 2022-12-28
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ServerCredentialService {
    private final ITokenHandler tokenHandler;
    private final ServerCredentialCacheConfig credentialCacheConfig;

    public ServerCredential create(ServerInfoEntity serverInfoEntity) {
        ServerCredential serverCredential = new ServerCredential();
        serverCredential.setServerId(serverInfoEntity.getId());
        serverCredential.setServerName(serverInfoEntity.getServerName());
        tokenHandler.createToken(serverCredential, credentialCacheConfig);
        return serverCredential;
    }

    public ServerCredential verifyAndGet(Long serverId, String token) {
        ServerCredential serverCredential = tokenHandler.verifyToken(token, ServerCredential.class, credentialCacheConfig);
        if (!serverId.equals(serverCredential.getServerId())) {
            throw new ParamErrorException("serverId 不匹配");
        }
        return serverCredential;
    }
}
