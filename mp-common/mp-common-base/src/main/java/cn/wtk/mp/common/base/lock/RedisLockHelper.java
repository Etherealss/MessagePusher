package cn.wtk.mp.common.base.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author wang tengkun
 * @date 2022/2/26
 */
@Component
@AutoConfigureAfter(RedisAutoConfiguration.class)
@Slf4j
public class RedisLockHelper {

    private final StringRedisTemplate stringRedisTemplate;
    private static final String SCRIPT_TEXT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    public RedisLockHelper(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 获取锁
     * @param lockKey lockKey
     * @param value value
     * @param timeout 超时时间
     * @param unit 过期单位
     * @return true or false
     */
    public boolean lock(String lockKey, final String value, long timeout, final TimeUnit unit) {
        Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, value, timeout, unit);
        return locked != null && locked;
    }

    /**
     * 非原子解锁，如果value不是uuid，则可能会解除别人的锁
     */
    public boolean unlock(String lockKey, String value) {
        if (StringUtils.hasText(lockKey) || StringUtils.hasText(value)) {
            return false;
        }
        boolean releaseLock = false;
        String requestId = stringRedisTemplate.opsForValue().get(lockKey);
        if (value.equals(requestId)) {
            releaseLock = Boolean.TRUE.equals(stringRedisTemplate.delete(lockKey));
        }
        return releaseLock;
    }

    /**
     * 使用lua脚本解锁，具有原子性
     */
    public boolean unlockLua(String lockKey, String value) {
        if (StringUtils.hasText(lockKey) || StringUtils.hasText(value)) {
            return false;
        }

        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        //用于解锁的lua脚本位置
        redisScript.setScriptText(SCRIPT_TEXT);
        redisScript.setResultType(Long.class);
        //没有指定序列化方式，默认使用上面配置的
        Object result = stringRedisTemplate.execute(
                redisScript,
                Collections.singletonList(lockKey),
                value
        );
        return Long.valueOf(1L).equals(result);
    }

}
