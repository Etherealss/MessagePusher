package cn.wtk.connect.domain.server;

import cn.wtk.connect.domain.server.app.connector.ConnectorKey;
import cn.wtk.connect.domain.server.app.connector.connection.Connection;

import java.util.Map;
import java.util.UUID;

/**
 * @author wtk
 * @date 2023-01-11
 */
public interface ConnComposite {
    void addConn(Connection conn);
    Connection removeConn(ConnectorKey connectorKey, UUID connId);
    Connection getConn(ConnectorKey connectorKey, UUID connId);
    Map<UUID, Connection> getConns(ConnectorKey connectorKey);
    boolean containsConn(ConnectorKey connectorKey);
    // TODO sendMsg
}
