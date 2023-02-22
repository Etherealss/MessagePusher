package cn.wtk.mp.msg.acceptor.infrasturcture.remote.redis;

import cn.wtk.mp.msg.acceptor.infrasturcture.config.TempIdProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

/**
 * @author wtk
 * @date 2023/2/21
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MsgTempIdRedisHandler {

    private static final String DEFAULT_VALUE = "1";

    private final RedisTemplate<String, String> redisTemplate;
    private final TempIdProperties tempIdProperties;

    public boolean add(UUID tempId) {
        String key = tempIdProperties.getCacheKey() + ":" + tempId.toString();
        Duration timeout = Duration.ofMillis(tempIdProperties.getExpireMs());
        // setIfAbsent 如果为空就set值，并返回1；如果存在(不为空)则不进行操作
        Boolean addSuccessful = redisTemplate.opsForValue().setIfAbsent(key, DEFAULT_VALUE, timeout);
        return Boolean.TRUE.equals(addSuccessful);
    }

    public boolean checkExist(UUID tempId) {
        String key = tempIdProperties.getCacheKey() + ":" + tempId.toString();
        return redisTemplate.opsForValue().get(key) != null;
    }

    public void remove(String tempIdKey) {
        redisTemplate.delete(tempIdKey);
    }
}
