package cn.wtk.mp.client.domain;

import cn.wtk.mp.client.infrastructure.config.ClientProperties;
import cn.wtk.mp.client.infrastructure.config.NettyProperties;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author wtk
 * @date 2023/3/27
 */
@Slf4j
@Component
public class NettyClient {
    @Getter
    private final NettyProperties nettyProperties;
    @Getter
    private final ClientProperties clientProperties;
    private final ApplicationEventPublisher publisher;
    private final Bootstrap bootstrap;
    private final ClientAuthHandler clientAuthHandler;

    private Channel channel;

    public NettyClient(NettyProperties nettyProperties,
                       ClientProperties clientProperties,
                       ApplicationEventPublisher publisher,
                       ClientAuthHandler clientAuthHandler) {
        this.nettyProperties = nettyProperties;
        this.clientProperties = clientProperties;
        this.publisher = publisher;
        this.clientAuthHandler = clientAuthHandler;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        this.bootstrap = new Bootstrap()
                .group(workerGroup)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 4, 0, 4, true))
                                //心跳检测
                                .addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS))
                                //客户端的逻辑
                                .addLast(new NettyClientHandler(NettyClient.this))
                                .addLast(new LengthFieldPrepender(4, 0))
                        ;
                    }
                });
        connectServer();
    }

    public void connectServer() {
        ChannelFuture future = bootstrap.connect(nettyProperties.getServerIp(), nettyProperties.getServerPort());
        //断线重连
        future.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                this.channel = channelFuture.channel();
                log.info("连接成功");
                clientAuthHandler.auth(this.channel);
            } else {
                final EventLoop loop = channelFuture.channel().eventLoop();
                loop.schedule(() -> {
                    log.info("连接服务器失败");
                    connectServer();
                }, 1L, TimeUnit.SECONDS);
            }
        });
    }

    public Channel getChannel() {
        return channel;
    }

    public void close() {
        if (this.channel != null) {
            this.channel.close();
        }
    }
}