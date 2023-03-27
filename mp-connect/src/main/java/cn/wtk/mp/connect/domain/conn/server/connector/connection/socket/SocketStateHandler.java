package cn.wtk.mp.connect.domain.conn.server.connector.connection.socket;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.connect.infrastructure.client.dto.ChannelMsg;
import cn.wtk.mp.connect.infrastructure.config.ChannelAttrKey;
import cn.wtk.mp.connect.infrastructure.event.ConnClosedEvent;
import cn.wtk.mp.connect.infrastructure.utils.MessageSender;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 实现对客户端 Channel 建立连接、断开连接、异常时的处理。
 * @author wtk
 * @date 2023-01-06
 */
@ChannelHandler.Sharable
@Component
@RequiredArgsConstructor
@Slf4j
public class SocketStateHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf byteBuf) {
        int readableBytes = byteBuf.readableBytes();
        byte[] bytes = new byte[readableBytes];
        byteBuf.readBytes(bytes);
        String requestData = new String(bytes, StandardCharsets.UTF_8);
        log.debug("收到消息：{}", requestData);
        MessageSender.send(ctx.channel(), new ChannelMsg(ApiInfo.OK, "ack:" + requestData));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("服务器发生了异常:", cause);
        ctx.close();
        publishConnClosedEvent(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("channelInactive");
        publishConnClosedEvent(ctx);
        super.channelInactive(ctx);
    }

    private void publishConnClosedEvent(ChannelHandlerContext ctx) {
        Long connectorId = ctx.channel().attr(ChannelAttrKey.CONNECTOR).getAndSet(null);
        UUID connId = ctx.channel().attr(ChannelAttrKey.CONN_ID).getAndSet(null);
        log.debug("移除连接：connectorId: {}, connId: {}", connectorId, connId);
        if (connectorId != null && connId != null) {
            applicationEventPublisher.publishEvent(new ConnClosedEvent(connectorId, connId));
        }
    }

}
