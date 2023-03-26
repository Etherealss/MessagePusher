package cn.wtk.mp.connect.domain.conn.server;

import cn.wtk.mp.connect.domain.conn.server.connector.connection.ConnAuthHandler;
import cn.wtk.mp.connect.domain.conn.server.connector.connection.ConnectionStateHandler;
import cn.wtk.mp.connect.infrastructure.config.NettyServerConfig;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolConfig;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.nio.ByteOrder;
import java.util.List;

/**
 * @author wtk
 * @date 2023-03-23
 */
public class SocketChooseHandler extends ByteToMessageDecoder {
    /** 默认暗号长度为23 */
    private static final int MAX_LENGTH = 23;
    /** WebSocket握手的协议前缀 */
    private static final String WEBSOCKET_PREFIX = "GET /";

    private final NettyServerConfig nettyServerConfig;
    private final ConnAuthHandler connAuthHandler;
    private final ConnectionStateHandler connectionStateHandler;
    private final ChannelLogHandler channelLogHandler;
    private final WebSocketServerProtocolHandler webSocketServerProtocolHandler;

    public SocketChooseHandler(NettyServerConfig nettyServerConfig,
                               ConnAuthHandler connAuthHandler,
                               ConnectionStateHandler connectionStateHandler,
                               ChannelLogHandler channelLogHandler) {
        this.nettyServerConfig = nettyServerConfig;
        this.connAuthHandler = connAuthHandler;
        this.connectionStateHandler = connectionStateHandler;
        this.channelLogHandler = channelLogHandler;
        // websocket协议配置
        WebSocketServerProtocolConfig webSocketConfig = WebSocketServerProtocolConfig.newBuilder()
                .websocketPath(nettyServerConfig.getPath())
                .allowExtensions(true)
                .maxFramePayloadLength(nettyServerConfig.getMaxFrameSize())
                .build();
        this.webSocketServerProtocolHandler = new WebSocketServerProtocolHandler(webSocketConfig);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String protocol = getBufStart(in);
        if (protocol.startsWith(WEBSOCKET_PREFIX)) {
            this.websocketAdd(ctx);
        } else {
            this.tcpAdd(ctx);
        }
        in.resetReaderIndex();
        ctx.pipeline().remove(this.getClass());
    }

    public void websocketAdd(ChannelHandlerContext ctx) {
        // HttpServerCodec：HTTP请求的解码和编码，用于ws握手
        ctx.pipeline()
                .addLast("httpServerCodec", new HttpServerCodec())
                // HttpServerCodec 会在每个HTTP消息中生成多个消息对象，
                // 使用下面的对象把多个消息转换为一个单一的 FullHttpRequest 或是 FullHttpResponse
                .addLast("httpObjectAggregator", new HttpObjectAggregator(65535))
                // ChunkedWriteHandler：处理大数据流
                .addLast("chunkedWriteHandler", new ChunkedWriteHandler())
                // 处理 WebSocket 数据压缩
                .addLast("webSocketServerCompressionHandler", new WebSocketServerCompressionHandler())
                .addLast("webSocketAggregator", new WebSocketFrameAggregator(65535))
                .addLast(channelLogHandler)
                .addLast("connAuthHandler", connAuthHandler)
                .addLast("connectionStateHandler", connectionStateHandler)
                // WebSocket 协议配置，一定要放在 connectorAuthHandler 之后
                // Auth 会对请求进行处理，处理后才能进过 ProtocolHandler 成功握手
                .addLast("protocolHandler", webSocketServerProtocolHandler)
        ;
    }

    public void tcpAdd(ChannelHandlerContext ctx) {
        ctx.pipeline()
                .addLast("lengthFieldBasedFrameDecoder", new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, 1024 * 1024, 0, 4, 0, 4, true))
                .addLast(channelLogHandler)
                .addLast("connAuthHandler", connAuthHandler)
                .addLast("connectionStateHandler", connectionStateHandler)
        ;
    }

    private String getBufStart(ByteBuf in) {
        int length = in.readableBytes();
        if (length > MAX_LENGTH) {
            length = MAX_LENGTH;
        }

        // 标记读位置
        in.markReaderIndex();
        byte[] content = new byte[length];
        in.readBytes(content);
        return new String(content);
    }
}