package cn.wtk.mp.connect.infrastructure.utils;

import cn.wtk.mp.connect.infrastructure.client.dto.ChannelMsg;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Slf4j
public class MessageSender {

    /**
     * TODO 异常处理、发送值、返回值
     * ChannelOutboundHandlerAdapter 会处理数据格式问题
     * @see cn.wtk.mp.connect.domain.conn.server.connector.connection.websocket.ToWebSocketFreamEncoder
     * @see cn.wtk.mp.connect.domain.conn.server.connector.connection.socket.ToByteEncoder
     * @param channel
     * @param data
     */
    public static void send(Channel channel, ChannelMsg data) {
        Objects.requireNonNull(channel);
        if (!channel.isActive()) {
            log.warn("Channel 未激活");
            return;
        }
        channel.writeAndFlush(data);
    }
}
