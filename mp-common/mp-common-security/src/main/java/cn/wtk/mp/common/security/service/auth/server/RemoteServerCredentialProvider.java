package cn.wtk.mp.common.security.service.auth.server;


import cn.wtk.mp.common.security.config.ServerCredentialConfig;
import cn.wtk.mp.common.security.feign.ServerCredentialFeign;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author wtk
 * @date 2022-10-17
 */
@AllArgsConstructor
public class RemoteServerCredentialProvider implements IServerCredentialProvider {
    private final ServerCredentialFeign serverCredentialFeign;
    private final ServerCredentialConfig config;
    private final RedisTemplate<String, ServerTokenCredential> redisTemplate;
    private final ServerDigestGenerator serverDigestGenerator;

    @Override
    public ServerTokenCredential create() {
        byte[] digest = serverDigestGenerator.generate(
                config.getServerId(), config.getServerName(), config.getSecret()
        );
        ServerAuthCommand command = new ServerAuthCommand(config.getServerId(), new String(digest));
        return serverCredentialFeign.createCredential(command);
    }
}
