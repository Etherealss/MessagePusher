package cn.wtk.mp.msg.acceptor.infrasturcture.remote.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author wtk
 * @date 2023/2/21
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MsgTempIdRedisHandler {

    private RedisTemplate<String, String> redisTemplate;
    public static final String DEFAULT_VALUE = "1";

    public void set(String tempIdKey, Duration timeout) {
        redisTemplate.opsForValue().setIfAbsent(tempIdKey, DEFAULT_VALUE, timeout);
    }

    public boolean checkExist(String tempIdKey, String tempId) {
        return redisTemplate.opsForValue().get(tempIdKey) != null;
    }

    public void remove(String tempIdKey) {
        redisTemplate.delete(tempIdKey);
    }
}
