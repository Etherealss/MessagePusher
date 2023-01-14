package cn.wtk.mp.connect.infrastructure.config;

import cn.wtk.mp.connect.domain.server.app.connector.ConnectorKey;
import io.netty.util.AttributeKey;

import java.util.UUID;

/**
 * @author wtk
 * @date 2023-01-14
 */
public class ChannelAttrKey {
    public static final AttributeKey<ConnectorKey> CONNECTOR = AttributeKey.valueOf("connectorKey");
    public static final AttributeKey<UUID> CONN_ID = AttributeKey.valueOf("connId");

}
