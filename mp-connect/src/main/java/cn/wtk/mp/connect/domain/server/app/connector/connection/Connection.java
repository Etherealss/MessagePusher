package cn.wtk.mp.connect.domain.server.app.connector.connection;

import cn.wtk.mp.connect.domain.server.app.connector.ConnectorKey;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Data
@AllArgsConstructor
public class Connection {
    private UUID connId;
    private ConnectorKey connectorKey;
    private ChannelHandlerContext ctx;

    public Connection(UUID connId, Long appId, Serializable connectorId, ChannelHandlerContext ctx) {
        this.connId = connId;
        this.connectorKey = new ConnectorKey(appId, connectorId);
        this.ctx = ctx;
    }

    public Serializable getConnectorId() {
        return this.connectorKey.getConnectorId();
    }

    public Long getAppId() {
        return this.connectorKey.getAppId();
    }
}
