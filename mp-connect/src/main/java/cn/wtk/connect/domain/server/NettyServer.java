package cn.wtk.connect.domain.server;

import cn.wtk.connect.infrastructure.config.NettyServerConfig;
import cn.wtk.connect.infrastructure.netty.NettyServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
@Getter
public class NettyServer implements ApplicationRunner, ApplicationListener<ContextClosedEvent> {

    private final NettyServerConfig config;
    private final NettyServerChannelInitializer nettyServerChannelInitializer;

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
    public void run(ApplicationArguments args) throws Exception {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(config.getIp(), config.getPort()))
                    .option(ChannelOption.SO_BACKLOG, config.getIdleSeconds())
                    .childOption(ChannelOption.SO_KEEPALIVE, config.getEnableTcpKeepalive())
                    .childOption(ChannelOption.TCP_NODELAY, config.getEnableTcpNodelay())
                    .childHandler(nettyServerChannelInitializer);
            Channel channel = serverBootstrap.bind().sync().channel();
            this.serverChannel = channel;
            log.info("websocket 服务启动，IP={}, port={}", config.getIp(), config.getPort());
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
        // TODO 清空 Redis 连接信息
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        log.info("websocket 服务停止");
    }
}
