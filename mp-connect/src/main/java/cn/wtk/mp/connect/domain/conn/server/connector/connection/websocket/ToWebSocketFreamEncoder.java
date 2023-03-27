package cn.wtk.mp.connect.domain.conn.server.connector.connection.websocket;

import cn.wtk.mp.common.base.utils.JsonUtil;
import cn.wtk.mp.connect.infrastructure.client.dto.ChannelMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 将数据进行 JSON 编码后，使用TextWebSocketFrame传输
 * @author wtk
 * @date 2023/3/27
 */
@ChannelHandler.Sharable
@Component
@RequiredArgsConstructor
@Slf4j
public class ToWebSocketFreamEncoder extends MessageToMessageEncoder<ChannelMsg> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ChannelMsg channelMsg, List<Object> list) throws Exception {
        log.debug("出站数据编码为 TextWebSocketFrame");
        String json = JsonUtil.toJsonString(channelMsg);
        list.add(new TextWebSocketFrame(json));
    }
}
