package cn.wtk.connect.infrastructure.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 实现客户端 Channel 断开连接、异常时的处理。
 * @author wtk
 * @date 2023-01-07
 */
@Component
@ChannelHandler.Sharable
@Slf4j
@RequiredArgsConstructor
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

}
