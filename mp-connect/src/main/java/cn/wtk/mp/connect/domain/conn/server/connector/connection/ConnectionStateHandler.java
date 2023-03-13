package cn.wtk.mp.connect.domain.conn.server.connector.connection;

import cn.wtk.mp.connect.infrastructure.config.ChannelAttrKey;
import cn.wtk.mp.connect.infrastructure.event.ConnClosedEvent;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

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
public class ConnectionStateHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) {
        log.debug("收到消息：{}", frame);
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) frame;
            String text = textWebSocketFrame.text();
            ctx.channel().writeAndFlush(new TextWebSocketFrame("ack:" + text));
        } else if (frame instanceof PingWebSocketFrame) {
            ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
        } else if (frame instanceof CloseWebSocketFrame) {
            log.debug("客户端关闭连接");
            publishConnClosedEvent(ctx);
        }
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
