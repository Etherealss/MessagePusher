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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author wtk
 * @date 2023/3/27
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MsgAuthHandler {
    public static final String AUTH_URL = "/auth?connectorToken=%s&appId=%d&connectorId=%d";
    private final AuthFeign authFeign;
    private final ConnectFeign connectFeign;
    private final MpClientProperties clientProperties;
    private final ServerCredentialConfig serverCredentialConfig;
    private final NettyClient nettyClient;

    @PostConstruct
    public void auth() {
        ConnectorCredential connectorCredential = this.getConnectorCredential();
        AuthMsg authMsg = buildAuthMsg(connectorCredential.getToken());
        MessageSendUtil.send(nettyClient.getChannel(), authMsg);
    }

    private ConnectorCredential getConnectorCredential() {
        Long connectorId = clientProperties.getConnectorId();
        ConnectorAddressDTO address = connectFeign.updateConnectAddress(connectorId);
        log.info("自动获取ConnectorAddress: {}", address);
        ConnectorCredential credential = authFeign.createCredential(
                serverCredentialConfig.getServerId(),
                connectorId,
                new CreateConnectCredentialCommand(address)
        );
        log.info("自动获取ConnectorCredential: {}", credential);
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
