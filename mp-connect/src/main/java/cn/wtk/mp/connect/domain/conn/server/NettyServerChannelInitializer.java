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
        // websocket协议配置
//        WebSocketServerProtocolConfig webSocketConfig = WebSocketServerProtocolConfig.newBuilder()
//                .websocketPath(nettyServerConfig.getPath())
//                .allowExtensions(true)
//                .maxFramePayloadLength(nettyServerConfig.getMaxFrameSize())
//                .build();
//        WebSocketServerProtocolHandler webSocketServerProtocolHandler = new WebSocketServerProtocolHandler(webSocketConfig);
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
//        socketChannel.pipeline()
//                .addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 4, 0, 4, true))
//                .addLast(channelLogHandler)
//                .addLast(connAuthHandler)
//                .addLast(socketStateHandler)
//                .addLast(toByteEncoder)
//        ;
//        socketChannel.pipeline().addLast(
//                // HTTP请求的解码和编码，用于ws握手
//                new HttpServerCodec(),
//                // 处理大数据流
//                new ChunkedWriteHandler(),
//                // HttpServerCodec 会在每个HTTP消息中生成多个消息对象，
//                // 使用下面的对象把多个消息转换为一个单一的 FullHttpRequest 或是 FullHttpResponse
//                new HttpObjectAggregator(65536),
//                // 处理 WebSocket 数据压缩
//                new WebSocketServerCompressionHandler(),
//                connAuthHandler,
//                // WebSocket 协议配置
//                webSocketServerProtocolHandler,
//                toWebSocketFreamEncoder
//        );
    }
}
