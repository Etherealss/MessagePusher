package cn.wtk.connect.infrastructure.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023-01-06
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class WebSocketMessageFrameHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        if (msg instanceof WebSocketMessage<?>) {
//            String json = JsonUtil.toJsonStr(msg);
//            super.write(ctx, new TextWebSocketFrame(json), promise);
//        }
        super.write(ctx, msg, promise);
    }
}