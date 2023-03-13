package cn.wtk.mp.connect.domain.conn.server.connector.connection;

import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Data
@AllArgsConstructor
public class Connection {
    private UUID connId;
    private Long connectorId;
    private Long appId;
    private ChannelHandlerContext ctx;
}
