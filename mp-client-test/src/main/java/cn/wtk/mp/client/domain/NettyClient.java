package cn.wtk.mp.client.domain;

import cn.wtk.mp.client.infrastructure.NettyClientProperties;
import cn.wtk.mp.client.infrastructure.event.AuthEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author wtk
 * @date 2023/3/27
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class NettyClient {
    private final NettyClientProperties properties;
    private Channel channel;
    private Bootstrap bootstrap = null;
    private final ApplicationEventPublisher publisher;

    @PostConstruct
    private void init() {
        bootstrap = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap.group(workerGroup)
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
        ChannelFuture f = bootstrap.connect(properties.getServerIp(), properties.getServerPort());
        //断线重连
        f.addListener((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess()) {
                channel = channelFuture.channel();
                log.info("连接成功，准备进行连接认证");
                publisher.publishEvent(new AuthEvent());
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