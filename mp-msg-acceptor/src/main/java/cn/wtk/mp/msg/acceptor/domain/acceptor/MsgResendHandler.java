package cn.wtk.mp.msg.acceptor.domain.acceptor;

import cn.wtk.mp.msg.acceptor.infrasturcture.config.MsgResendProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

/**
 * @author wtk
 * @date 2023-02-12
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MsgResendHandler {
    private static final String DEFAULT_VALUE = "1";

    private final RedisTemplate<String, String> redisTemplate;
    private final MsgResendProperties msgResendProperties;

    /**
     * 检查消息是否重复
     * @param tempId 消息临时 ID，用于标识客户端发送的消息，可用于去重
     * @return 重复消息返回 true，首次接受到的消息返回 false
     */
    public boolean checkAndSet4Duplicate(UUID tempId) {
        String tempIdKey = msgResendProperties.getCacheKey() + ":" + tempId.toString();
        Duration duration = Duration.ofMillis(msgResendProperties.getExpireMs());
        // setIfAbsent 如果为空就set值，并返回1；如果存在(不为空)则不进行操作
        Boolean addSuccessful = redisTemplate.opsForValue().setIfAbsent(tempIdKey, DEFAULT_VALUE, duration);
        // 添加成功返回 true，也就是说，返回 true 说明是第一次传输，redis 没有重复记录
        // 如果 addSuccessful==false，说明已收到过该消息，则当前消息为重复消息，返回 true
        boolean dupilicate = Boolean.FALSE.equals(addSuccessful);
        return dupilicate;
    }
}
