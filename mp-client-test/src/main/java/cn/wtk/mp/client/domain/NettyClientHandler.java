package cn.wtk.mp.client.domain;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author wtk
 * @date 2023/3/27
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private NettyClient nettyClient;
    private String tenantId;
    private int attempts = 0;

    public NettyClientHandler(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        int readableBytes = byteBuf.readableBytes();
        byte[] bytes = new byte[readableBytes];
        byteBuf.readBytes(bytes);
        String jsonData = new String(bytes, StandardCharsets.UTF_8);
        log.info("连接者 {} 收到服务器推送消息: {}", nettyClient.getClientProperties().getConnectorId(), jsonData);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接建立！");
        attempts = 0;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接失活，尝试断线重连");
        //使用过程中断线重连
        final EventLoop eventLoop = ctx.channel().eventLoop();
        if (attempts < 12) {
            attempts++;
        }
        int timeout = 2 << attempts;
        eventLoop.schedule(() -> nettyClient.connectServer(), timeout, TimeUnit.SECONDS);
        ctx.fireChannelInactive();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                log.info("READER_IDLE");
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                //发送心跳，保持长连接
                String s = "NettyClient" + System.getProperty("line.separator");
                ctx.channel().writeAndFlush(s);  //发送心跳成功
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                log.info("ALL_IDLE");
            }
        }
        super.userEventTriggered(ctx, evt);
    }
}