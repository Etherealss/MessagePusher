package cn.wtk.mp.auth.application;

import cn.wtk.mp.auth.domain.credential.TokenCredentialService;
import cn.wtk.mp.auth.domain.credential.TokenCredentialSpec;
import cn.wtk.mp.auth.infrastructure.client.command.CreateConnectCredentialCommand;
import cn.wtk.mp.auth.infrastructure.config.ConnectorCredentialAuthConfig;
import cn.wtk.mp.common.base.exception.rest.ParamErrorException;
import cn.wtk.mp.common.security.service.auth.connector.ConnectorCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @date 2023-01-04
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectorAuthenticationAppService {
    private final ConnectorCredentialAuthConfig credentialCacheConfig;
    private final TokenCredentialService tokenCredentialService;
    private final ConnectorCredentialAuthConfig config;

    public ConnectorCredential create(Long appId, Long connectorId,
                                      CreateConnectCredentialCommand command) {
        ConnectorCredential connectorCredential = new ConnectorCredential();
        connectorCredential.setAppId(appId);
        connectorCredential.setConnectorId(connectorId);
        connectorCredential.setConnectIp(command.getConnectIp());
        connectorCredential.setConnectPort(command.getConnectPort());
        TokenCredentialSpec tokenSpec = new TokenCredentialSpec(
                config.getTokenTopic(),
                config.getExpireMs()
        );
        tokenCredentialService.completeAndSave(connectorCredential, tokenSpec);
        return connectorCredential;
    }

    public ConnectorCredential verifyAndGet(Long appId,
                                            Long connectorId,
                                            String token,
                                            String connectIp,
                                            Integer connectPort) {
        ConnectorCredential connectorCredential = tokenCredentialService.verifyAndGet(
                token, ConnectorCredential.class
        );
        if (!connectorId.equals((connectorCredential.getConnectorId()))) {
            throw new ParamErrorException("connectorCredential connectorId 不匹配");
        }
        if (!appId.equals((connectorCredential.getAppId()))) {
            throw new ParamErrorException("connectorCredential appId 不匹配");
        }
        if (connectIp.equals(connectorCredential.getConnectIp()) &&
                connectPort.equals(connectorCredential.getConnectPort())) {
            return connectorCredential;

        }
        throw new ParamErrorException("connectorCredential 路由地址不匹配");
    }
}
