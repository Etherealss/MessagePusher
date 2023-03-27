package cn.wtk.mp.connect.domain.conn.server;

import cn.wtk.mp.connect.domain.conn.server.connector.connection.ConnAuthHandler;
import cn.wtk.mp.connect.domain.conn.server.connector.connection.socket.SocketStateHandler;
import cn.wtk.mp.connect.domain.conn.server.connector.connection.socket.ToByteEncoder;
import cn.wtk.mp.connect.domain.conn.server.connector.connection.websocket.ToWebSocketFreamEncoder;
import cn.wtk.mp.connect.domain.conn.server.connector.connection.websocket.WebSocketStateHandler;
import cn.wtk.mp.connect.infrastructure.config.NettyServerConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
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
    private final ConnAuthHandler connAuthHandler;
    private final WebSocketStateHandler webSocketStateHandler;
    private final ChannelLogHandler channelLogHandler;
    private final SocketStateHandler socketStateHandler;
    private final ToByteEncoder toByteEncoder;
    private final ToWebSocketFreamEncoder toWebSocketFreamEncoder;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        // 协议选择。用于选择WebSocket还是Socket
        socketChannel.pipeline().addLast(new SocketChooseHandler(
                nettyServerConfig,
                connAuthHandler,
                webSocketStateHandler,
                channelLogHandler,
                socketStateHandler,
                toByteEncoder,
                toWebSocketFreamEncoder
        ));
    }
}
