package cn.wtk.mp.connect.domain.conn.server.connector.connection.socket;

import cn.wtk.mp.common.base.utils.JsonUtil;
import cn.wtk.mp.connect.infrastructure.client.dto.ChannelMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 将数据进行 JSON 编码后，使用 ByteBuf 传输
 * @author wtk
 * @date 2023/3/27
 */
@ChannelHandler.Sharable
@Component
@RequiredArgsConstructor
@Slf4j
public class ToByteEncoder extends MessageToByteEncoder<ChannelMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ChannelMsg channelMsg, ByteBuf out) throws Exception {
        log.debug("出站数据编码为 byte");
        byte[] bytes = JsonUtil.toJsonString(channelMsg).getBytes(StandardCharsets.UTF_8);
        out.writeBytes(bytes);
    }
}
