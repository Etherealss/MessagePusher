package cn.wtk.mp.connect.domain.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023-01-06
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class MsgReceiverHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("收到消息：{}", msg);
        super.channelRead(ctx, msg);
    }
}