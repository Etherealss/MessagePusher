package cn.wtk.mp.connect.domain.server;

import cn.wtk.mp.connect.domain.server.app.connector.ConnectorKey;
import cn.wtk.mp.connect.infrastructure.config.ChannelAttrKey;
import io.netty.channel.ChannelFutureListener;
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

    private final ServerConnectionManager serverConnectionManager;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) {
        log.info("收到消息：{}", msg);
        if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;
            String text = textWebSocketFrame.text();
//            ctx.channel().writeAndFlush(new WebSocketMessage<>("pong", text));
            ctx.channel().writeAndFlush("ack:" + text);
        } else if (msg instanceof PingWebSocketFrame) {
            ctx.channel().writeAndFlush(new PongWebSocketFrame(msg.content().retain()));
        } else {
            ctx.channel().writeAndFlush(WebSocketCloseStatus.INVALID_MESSAGE_TYPE).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        removeConn(ctx);
        ctx.close();
        log.warn("服务器发生了异常:", cause);
    }

    private void removeConn(ChannelHandlerContext ctx) {
        ConnectorKey connectorKey = ctx.channel().attr(ChannelAttrKey.CONNECTOR).get();
        UUID connId = ctx.channel().attr(ChannelAttrKey.CONN_ID).get();
        serverConnectionManager.removeConn(connectorKey, connId);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        removeConn(ctx);
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        removeConn(ctx);
        super.channelUnregistered(ctx);
    }
}
