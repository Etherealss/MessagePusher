package cn.wtk.connect.domain.server;

import cn.wtk.connect.infrastructure.netty.ConnectionStateHandler;
import cn.wtk.mp.common.base.exception.service.NotFoundException;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.isNull;

/**
 * @author wtk
 * @date 2023-01-06
 */
@Component
@Slf4j
public class ServerConnectionManager {

    private final Map<String, ChannelHandlerContext> onlineTerminalMap = new ConcurrentHashMap<>();

    /**
     * 添加terminalId信息
     */
    public void put(String terminalId, ChannelHandlerContext ctx) {
        onlineTerminalMap.put(terminalId, ctx);
        log.info("终端 [ {} ] 创建连接", terminalId);
    }

    /***
     * 删除terminalId信息
     * */
    public String remove(ChannelHandlerContext ctx) {
        if (ctx == null) {
            return null;
        }
        String key = null;
        for (Map.Entry<String, ChannelHandlerContext> channelHandlerContextEntry : onlineTerminalMap.entrySet()) {
            if (ctx == channelHandlerContextEntry.getValue()) {
                key = channelHandlerContextEntry.getKey();
                break;
            }
        }
        if (key == null) {
            return null;
        }
        onlineTerminalMap.remove(key);
        log.info("终端 [ {} ] 断开连接 ", key);
        return key;
    }

    /**
     * 根据 terminalId 使用新的ctx替换旧的ctx
     * @param newCtx
     * @param terminalId
     */
    public void replaceOldChannel(ChannelHandlerContext newCtx, String terminalId) {
        if (onlineTerminalMap.containsKey(terminalId)) {
            // 关闭旧连接
            ChannelHandlerContext oldCtx = onlineTerminalMap.remove(terminalId);
            String oldTerminalId = oldCtx.channel().attr(ConnectionStateHandler.TERMINAL_ID_KEY).get();
            String oldRandomCode = oldCtx.channel().attr(ConnectionStateHandler.RANDOM_CODE_KEY).get();
            oldCtx.close();
        }
        onlineTerminalMap.put(terminalId, newCtx);
    }

    public ChannelHandlerContext getCtxByTerminalId(String terminalId) {
        if (isNull(onlineTerminalMap.get(terminalId))) {
            throw new NotFoundException("终端不存在");
        }
        return onlineTerminalMap.get(terminalId);
    }

    public void sendMessageByTerminalId(String terminalId, WebSocketMessage<?> msg) {
        this.getCtxByTerminalId(terminalId).channel().writeAndFlush(msg);
    }
}
