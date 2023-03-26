package cn.wtk.mp.connect.domain.conn.server;

import cn.wtk.mp.connect.domain.conn.server.connector.connection.ConnAuthHandler;
import cn.wtk.mp.connect.domain.conn.server.connector.connection.ConnectionStateHandler;
import cn.wtk.mp.connect.infrastructure.config.NettyServerConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 配置netty的各种Channel及其次序
 * @author wtk
 * @date 2023-01-07
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final NettyServerConfig nettyServerConfig;
    private final IdleTimeoutHandlerAdapter idleTimeoutHandlerAdapter;
    private final MsgPushHandler msgPushHandler;
    private final ChannelLogHandler channelLogHandler;
    private final ConnAuthHandler connAuthHandler;
    private final ConnectionStateHandler connectionStateHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        // websocket协议配置
        WebSocketServerProtocolConfig webSocketConfig = WebSocketServerProtocolConfig.newBuilder()
                .websocketPath(nettyServerConfig.getPath())
                .allowExtensions(true)
                .maxFramePayloadLength(nettyServerConfig.getMaxFrameSize())
                .build();
        WebSocketServerProtocolHandler webSocketServerProtocolHandler =
                new WebSocketServerProtocolHandler(webSocketConfig);
        // 协议选择。用于选择WebSocket还是Socket
        SocketChooseHandler handler = new SocketChooseHandler(
                nettyServerConfig,
                connAuthHandler,
                connectionStateHandler,
                channelLogHandler
        );
        socketChannel.pipeline()
                .addLast("socketChooseHandler", handler)
        ;
    }
}
