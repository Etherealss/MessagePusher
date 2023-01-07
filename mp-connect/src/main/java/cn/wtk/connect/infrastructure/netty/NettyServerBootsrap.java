package cn.wtk.connect.infrastructure.netty;

import cn.wtk.connect.infrastructure.config.NettyServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;


/**
 * @author wtk
 * @date 2023-01-06
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class NettyServerBootsrap implements ApplicationRunner, ApplicationListener<ContextClosedEvent>, ApplicationContextAware {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final NettyServerConfig nettyServerConfig;
    private final NettyServerChannelInitializer nettyServerChannelInitializer;

    private ApplicationContext applicationContext;
    private Channel serverChannel;
    /**
     * Boss 线程组，用于服务端接受客户端的连接
     */
    private EventLoopGroup bossGroup;
    /**
     * Worker 线程组，用于服务端接受客户端的数据读写
     */
    private EventLoopGroup workerGroup;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(nettyServerConfig.getPort()))
                    .option(ChannelOption.SO_BACKLOG, nettyServerConfig.getIdleSeconds())
                    .childOption(ChannelOption.SO_KEEPALIVE, nettyServerConfig.getEnableTcpKeepalive())
                    .childOption(ChannelOption.TCP_NODELAY, nettyServerConfig.getEnableTcpNodelay())
                    .childHandler(nettyServerChannelInitializer);
            Channel channel = serverBootstrap.bind().sync().channel();
            this.serverChannel = channel;
            log.info("websocket 服务启动，port={}", nettyServerConfig.getPort());
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * 关闭 netty server
     * @param event the event to respond to
     */
    @PreDestroy
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (this.serverChannel != null) {
            this.serverChannel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        log.info("websocket 服务停止");
    }
}
