package cn.wtk.connect.domain.server;

import cn.wtk.connect.domain.server.ServerConnectionManager;
import com.mongodb.event.ConnectionClosedEvent;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.AttributeKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

    public static final AttributeKey<String> CONNECTOR_ID = AttributeKey.valueOf("connectorId");
    public static final AttributeKey<String> CONN_ID = AttributeKey.valueOf("connId");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String connectToken = null;
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            String origin = request.headers().get("Origin");
            if (null != uri && uri.contains("?connectToken")) {
                connectToken = getConnectTokenByUri(uri);
                // WebSocketServerProtocolHandler内部会通过URI与配置文件的URI做比对
                // 如果URI一致，才会通过握手建立WebSocket连接
                request.setUri("/");
            } else {
                log.info("不允许终端 [ {} ] 连接，强制断开", origin);
                ctx.close();
            }
        }
        super.channelRead(ctx, msg);
        if (StringUtils.hasText(connectToken)) {
            applicationEventPublisher.publishEvent(new ConnectionEstablishedEvent(connectToken, ctx));
        }
    }

    private String getConnectTokenByUri(String uri) {
        String terminalId = null;
        String[] uriArray = uri.split("\\?");
        if (uriArray.length > 1) {
            String[] paramsArray = uriArray[1].split("=");
            if (paramsArray.length > 1) {
                terminalId = paramsArray[1];
            }
        }
        return terminalId;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) {
        if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;
            String text = textWebSocketFrame.text();
            ctx.channel().writeAndFlush(new WebSocketMessage<>("pong", text));
        } else if (msg instanceof PingWebSocketFrame) {
            ctx.channel().writeAndFlush(new PongWebSocketFrame(msg.content().retain()));
        } else {
            ctx.channel().writeAndFlush(WebSocketCloseStatus.INVALID_MESSAGE_TYPE).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        serverConnectionManager.remove(ctx);
        publishConnectionClosedEvent(ctx);
        ctx.close();
        log.error("服务器发生了异常: [ {} ]", cause);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        publishConnectionClosedEvent(ctx);
        serverConnectionManager.remove(ctx);
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        publishConnectionClosedEvent(ctx);
        serverConnectionManager.remove(ctx);
        super.channelUnregistered(ctx);
    }

    private void publishConnectionClosedEvent(ChannelHandlerContext ctx) {
        String terminalId = ctx.channel().attr(CONN_ID).get();
        String randomCode = ctx.channel().attr(CONNECTOR_ID).get();
        applicationEventPublisher.publishEvent(new ConnectionClosedEvent(terminalId, randomCode));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("链接创建：{}", ctx.channel().remoteAddress());
    }
}
