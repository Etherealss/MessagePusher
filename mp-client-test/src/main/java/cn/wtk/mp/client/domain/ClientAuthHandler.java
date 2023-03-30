package cn.wtk.mp.client.domain;

import cn.wtk.mp.client.infrastructure.config.MpClientProperties;
import cn.wtk.mp.client.infrastructure.pojo.dto.AuthMsg;
import cn.wtk.mp.client.infrastructure.remote.command.CreateConnectCredentialCommand;
import cn.wtk.mp.client.infrastructure.remote.dto.ConnectorAddressDTO;
import cn.wtk.mp.client.infrastructure.remote.feign.AuthFeign;
import cn.wtk.mp.client.infrastructure.remote.feign.ConnectFeign;
import cn.wtk.mp.client.infrastructure.utils.MessageSendUtil;
import cn.wtk.mp.common.security.config.ServerCredentialConfig;
import cn.wtk.mp.common.security.service.auth.connector.ConnectorCredential;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023/3/27
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ClientAuthHandler {
    public static final String AUTH_URL = "/auth?connectorToken=%s&appId=%d&connectorId=%d";
    private final AuthFeign authFeign;
    private final ConnectFeign connectFeign;
    private final MpClientProperties clientProperties;
    private final ServerCredentialConfig serverCredentialConfig;

    public void auth(Channel serverChannel) {
        log.info("开始验证操作");
        ConnectorCredential connectorCredential = this.getConnectorCredential();
        AuthMsg authMsg = buildAuthMsg(connectorCredential.getToken());
        MessageSendUtil.send(serverChannel, authMsg);
    }

    private ConnectorCredential getConnectorCredential() {
        Long connectorId = clientProperties.getConnectorId();
        ConnectorAddressDTO address = connectFeign.updateConnectAddress(connectorId);
        log.info("connectorId: {} 自动获取 ConnectorAddress: {}", connectorId, address);
        ConnectorCredential credential = authFeign.createCredential(
                serverCredentialConfig.getServerId(),
                connectorId,
                new CreateConnectCredentialCommand(address)
        );
        log.info("connectorId: {}  自动获取ConnectorCredential: {}", connectorId, credential);
        return credential;
    }

    private AuthMsg buildAuthMsg(String connectorToken) {
        String authUrl = String.format(
                AUTH_URL,
                connectorToken,
                serverCredentialConfig.getServerId(),
                clientProperties.getConnectorId()
        );
        log.info("auth url: {}", authUrl);
        return new AuthMsg(authUrl);
    }
}
