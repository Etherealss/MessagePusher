package cn.wtk.mp.connect.domain.server.connector.connection;

import cn.wtk.mp.common.base.utils.JsonUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Component
@Slf4j
public class MessageSender {

    /**
     * TODO 异常处理、发送值、返回值
     * @param channel
     * @param data
     */
    public void send(Channel channel, Object data) {
        if (channel == null) {
            log.warn("Channel 为空");
            throw new NullPointerException("Channel 为空");
        }
        if (!channel.isActive()) {
            log.warn("Channel 未激活");
            return;
        }
        channel.writeAndFlush(new TextWebSocketFrame(JsonUtil.toJsonString(data)));
    }
}
