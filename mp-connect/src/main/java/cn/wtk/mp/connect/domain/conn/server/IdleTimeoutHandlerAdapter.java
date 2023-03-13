package cn.wtk.mp.connect.domain.conn.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 心跳
 * @author wtk
 * @date 2023-01-06
 */
@ChannelHandler.Sharable
@Slf4j
@Component
public class IdleTimeoutHandlerAdapter extends SimpleChannelInboundHandler<PingWebSocketFrame> {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                log.info("WebSocket连接空闲超时，关闭连接");
                ctx.close();
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PingWebSocketFrame ping) throws Exception {
        log.info("收到心跳包：{}", ping);
        ctx.channel().writeAndFlush(new PongWebSocketFrame(ping.content().retain()));
    }
}