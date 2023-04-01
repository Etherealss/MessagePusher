package cn.wtk.mp.connect.domain.conn.server.connector.connection;

import cn.wtk.mp.connect.domain.msg.connector.TransferMsg;
import cn.wtk.mp.connect.infrastructure.client.dto.ChannelMsg;
import cn.wtk.mp.connect.infrastructure.utils.MessageSender;
import io.netty.channel.ChannelHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Data
@AllArgsConstructor
@Slf4j
public class Connection {
    private UUID connId;
    private Long connectorId;
    private Long appId;
    private ChannelHandlerContext ctx;
    private String ip;
    private Integer port;

    public void pushMsg(TransferMsg msg) {
        if (ip.equals(msg.getSendIp()) && port.equals(msg.getSendPort())) {
            log.info("相同IP和端口的连接不处理");
            return;
        }
        MessageSender.send(ctx.channel(), new ChannelMsg(msg));
    }
}
