package cn.wtk.mp.client.infrastructure.utils;

import cn.wtk.mp.client.infrastructure.pojo.dto.ChannelMsgBody;
import cn.wtk.mp.common.base.utils.JsonUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Slf4j
public class MessageSendUtil {

    /**
     * TODO 异常处理、发送值、返回值
     * @param channel
     * @param data
     */
    public static void send(Channel channel, ChannelMsgBody data) {
        if (channel == null) {
            log.warn("Channel 为空");
            throw new NullPointerException("Channel 为空");
        }
        if (!channel.isActive()) {
            log.warn("Channel 未激活");
            return;
        }
        String text = JsonUtil.toJsonString(data);
        log.debug("发送消息：{}", text);
        ByteBuf byteBuf = Unpooled.copiedBuffer(text.getBytes(StandardCharsets.UTF_8));
        channel.writeAndFlush(byteBuf);
    }
}
