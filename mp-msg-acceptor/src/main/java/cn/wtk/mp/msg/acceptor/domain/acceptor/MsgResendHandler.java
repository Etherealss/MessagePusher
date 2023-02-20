package cn.wtk.mp.msg.acceptor.domain.acceptor;

import cn.wtk.mp.common.msg.entity.AbstractMsg;
import cn.wtk.mp.msg.acceptor.infrasturcture.config.ResendProperties;
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

    private RedisTemplate<String, String> redisTemplate;
    private final ResendProperties resendProperties;

    public boolean handleMsgDuplicate(AbstractMsg msg, UUID tempMsgId) {
        if (msg.getResend()) {
            String key = resendProperties.getCacheKey() + ":" + tempMsgId.toString();
            Boolean absent = redisTemplate.opsForValue().setIfAbsent(key, "1");
            if (Boolean.TRUE.equals(absent)) {
                redisTemplate.expire(key, Duration.ofMillis(resendProperties.getExpireMs()));
                return false;
            } else {
                // 如果之前在redis上不存在该消息，则说明是第一次传输，返回 false
                // 如果abent==false，说明已收到过该消息，则当前消息为重复消息，返回true
                return true;
            }
        }
        // msg 不是重传的，直接返回 false
        return false;
    }
}
