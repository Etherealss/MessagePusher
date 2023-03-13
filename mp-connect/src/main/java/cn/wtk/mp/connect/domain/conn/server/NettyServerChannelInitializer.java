package cn.wtk.mp.connect.domain.conn.server;

import cn.wtk.mp.connect.domain.conn.server.connector.device.ConnAuthHandler;
import cn.wtk.mp.connect.domain.conn.server.connector.device.ConnectionStateHandler;
import cn.wtk.mp.connect.infrastructure.config.NettyServerConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
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
        ChannelHandler[] handlers = {
                channelLogHandler,
                // HTTP请求的解码和编码，用于ws握手
                new HttpServerCodec(),
                // 处理大数据流
                new ChunkedWriteHandler(),
                // HttpServerCodec 会在每个HTTP消息中生成多个消息对象，
                // 使用下面的对象把多个消息转换为一个单一的 FullHttpRequest 或是 FullHttpResponse
                new HttpObjectAggregator(65536),
//                // 处理 WebSocket 数据压缩
                new WebSocketServerCompressionHandler(),
                connAuthHandler,
                connectionStateHandler,
                // WebSocket 协议配置，一定要放在 connectorAuthHandler 之后
                // Auth 会对请求进行处理，处理后才能进过 ProtocolHandler 成功握手
                webSocketServerProtocolHandler,
        };
        // 按序添加Handler
        socketChannel.pipeline().addLast(handlers);
    }
}
