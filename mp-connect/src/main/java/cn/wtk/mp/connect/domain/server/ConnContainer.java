package cn.wtk.mp.connect.domain.server;

import cn.wtk.mp.connect.domain.server.app.connector.ConnectorKey;
import cn.wtk.mp.connect.domain.server.app.connector.connection.Connection;

import java.util.UUID;

/**
 * @author wtk
 * @date 2023-01-11
 */
public interface ConnContainer {
    void addConn(Connection conn);
    Connection removeConn(ConnectorKey connectorKey, UUID connId);
    Connection getConn(ConnectorKey connectorKey, UUID connId);
    // TODO sendMsg
}