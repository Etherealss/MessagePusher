package cn.wtk.mp.common.base.lock;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.UUID;


/**
 * @author wang tengkun
 * @date 2022/2/26
 */
@Aspect
@Slf4j
@Configuration
public class LockMethodInterceptor {

    private final RedisLockOperator redisLockOperator;
    private final CacheKeyGenerator cacheKeyGenerator;

    private final String lockPrefix;

    @Autowired
    public LockMethodInterceptor(RedisLockOperator redisLockOperator,
                                 CacheKeyGenerator cacheKeyGenerator,
                                 @Value("${mp.common.lock.key-prefix}") String lockPrefix) {
        this.redisLockOperator = redisLockOperator;
        this.cacheKeyGenerator = cacheKeyGenerator;
        this.lockPrefix = lockPrefix;
    }

    /**
     * @see cn.wtk.mp.common.base.lock.CacheLock
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(public * *(..)) && @annotation(cn.wtk.mp.common.base.lock.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if (!StringUtils.hasText(lock.key())) {
            log.error("@CacheLock 指定的key不能为空");
            throw new RuntimeException("cache lock key should not be empty");
        }

        final String lockKey = lockPrefix + cacheKeyGenerator.getLockKey(pjp);
        final String lockFlag = UUID.randomUUID().toString();
        long startTime = -1;
        try {
            // 循环重试
            for (int retryTimes = lock.retryTimes(); retryTimes >= 0; retryTimes--) {
                boolean lockSuccess = redisLockOperator.lock(lockKey, lockFlag, lock.expire(), lock.timeUnit());
                if (lockSuccess) {
                    // 获取锁成功
                    log.debug("获取分布式锁成功，key: {}, value: {}，重试次数: {}", lockKey, lockFlag, lock.retryTimes() - retryTimes);
                    startTime = System.currentTimeMillis();
                    return pjp.proceed();
                }
                // 获取锁失败
                long sleepMs = lock.retryIntervalMs();
                log.debug("获取分布式锁失败。key: {}, value: {}，剩余重试次数：{}，睡眠时间：{}ms", lockKey, lockFlag, retryTimes, sleepMs);
                if (sleepMs > 0) {
                    try {
                        Thread.sleep(sleepMs);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
            // 重试次数用尽也没有获取锁
            if (lock.lockFailedThrowException()) {
                throw new BaseException(ApiInfo.SERVER_BUSY, "业务繁忙，请稍后重试");
            } else {
                return null;
            }
        } finally {
            // 释放锁
            redisLockOperator.unlockLua(lockKey, lockFlag);
            log.debug("释放分布式锁成功，key: {}, value: {}, 占有锁的时长: {}ms", lockKey, lockFlag, startTime > 0 ? System.currentTimeMillis() - startTime : 0);
        }
    }
}
