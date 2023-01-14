package cn.wtk.connect.domain.server;

import cn.wtk.connect.domain.server.app.connector.ConnectorKey;
import cn.wtk.connect.domain.server.app.connector.connection.Connection;
import cn.wtk.connect.domain.server.app.connector.connection.MessageSender;
import cn.wtk.mp.common.base.utils.UUIDUtil;
import cn.wtk.mp.common.base.utils.UrlUtil;
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

import java.util.Map;
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

    private static final AttributeKey<ConnectorKey> ATTRIBUTE_KEY_CONNECTOR_KEY
            = AttributeKey.valueOf("connectorKey");
    private static final AttributeKey<UUID> ATTRIBUTE_KEY_CONN_ID = AttributeKey.valueOf("connId");

    private final ServerConnectionManager serverConnectionManager;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ConnectTokenAuthService connectTokenAuthService;
    private final MessageSender messageSender;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String connectToken = null;
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            String origin = request.headers().get("Origin");
            Map<String, String> urlParameters = UrlUtil.getUrlParameters(uri);
            AuthResult authResult = connectTokenAuthService.doAuth(urlParameters);
            // TODO WebSocketServerProtocolHandler内部会通过URI与配置文件的URI做比对，如果URI一致，才会通过握手建立WebSocket连接
//            request.setUri("/");
            if (!authResult.isSuccess()) {
                // TODO 发送给客户端的数据结构
                messageSender.send(ctx.channel(), authResult.getErrorMsg());
                ctx.close();
                return;
            }
            // 认证完毕，可以建立连接
            ConnectorKey connectorKey = new ConnectorKey(
                    authResult.getAppId(),
                    authResult.getConnectorId()
            );
            UUID connId = UUIDUtil.get();
            Connection conn = new Connection(connId, connectorKey, ctx);
            ctx.channel().attr(ATTRIBUTE_KEY_CONNECTOR_KEY).set(connectorKey);
            ctx.channel().attr(ATTRIBUTE_KEY_CONN_ID).set(connId);
            serverConnectionManager.addConn(conn);
        }
        super.channelRead(ctx, msg);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) {
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
        ConnectorKey connectorKey = ctx.channel().attr(ATTRIBUTE_KEY_CONNECTOR_KEY).get();
        UUID connId = ctx.channel().attr(ATTRIBUTE_KEY_CONN_ID).get();
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

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ConnectorKey connectorKey = ctx.channel().attr(ATTRIBUTE_KEY_CONNECTOR_KEY).get();
        UUID connId = ctx.channel().attr(ATTRIBUTE_KEY_CONN_ID).get();
        log.info("链接创建：RemoteIP={}, connectorKey={}, connId={}",
                ctx.channel().remoteAddress(), connectorKey, connId
        );
    }
}
