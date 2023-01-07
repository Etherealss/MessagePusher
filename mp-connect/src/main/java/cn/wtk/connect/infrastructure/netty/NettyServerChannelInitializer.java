package cn.wtk.connect.infrastructure.netty;

import cn.wtk.connect.infrastructure.config.NettyServerConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
    private final NettyServerHandler nettyServerHandler;
    private final IdleTimeoutHandlerAdapter idleTimeoutHandlerAdapter;
    private final WebSocketMessageFrameHandler webSocketMessageFrameHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        // websocket协议配置
        WebSocketServerProtocolHandler webSocketServerProtocolHandler = new WebSocketServerProtocolHandler(
                WebSocketServerProtocolConfig.newBuilder()
                        .websocketPath(nettyServerConfig.getPath())
                        .allowExtensions(true)
                        .maxFramePayloadLength(nettyServerConfig.getMaxFrameSize())
                        .build()
        );
        ChannelHandler[] handlers = {
                // Channel空闲监听
                new IdleStateHandler(0, 0, nettyServerConfig.getIdleSeconds(), TimeUnit.SECONDS),
                // HTTP请求的解码和编码
                new HttpServerCodec(),
                // 处理大数据流
                new ChunkedWriteHandler(),
                // HttpServerCodec 会在每个HTTP消息中生成多个消息对象，
                // 使用下面的对象把多个消息转换为一个单一的 FullHttpRequest 或是 FullHttpResponse
                new HttpObjectAggregator(65536),
                // 处理 WebSocket 数据压缩
                new WebSocketServerCompressionHandler(),
                webSocketServerProtocolHandler,
                // WebSocket 协议配置
                webSocketServerProtocolHandler,
                idleTimeoutHandlerAdapter,
                webSocketMessageFrameHandler,
        };
        // 按序添加Handler
        socketChannel.pipeline().addLast(handlers);
    }
}
