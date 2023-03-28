package cn.wtk.mp.common.base.lock;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author wtk
 * @date 2023/3/28
 */
@Slf4j
@AllArgsConstructor
@Builder
public class RedisLockHelper {
    private final RedisLockOperator redisLockOperator;
    private final int totalRetryTimes;
    private final int retryIntervalMs;
    private final boolean lockFailedThrowException;

    public void lock(String lockKey, String lockFlag, long expireTime) {
        this.lock(lockKey, lockFlag, expireTime, TimeUnit.MILLISECONDS);
    }

    public void lock(String lockKey, String lockFlag, long expireTime, TimeUnit timeUnit) {
        // 循环重试
        for (int retryTimes = totalRetryTimes; retryTimes >= 0; retryTimes--) {
            boolean lockSuccess = redisLockOperator.lock(lockKey, lockFlag, expireTime, timeUnit);
            if (lockSuccess) {
                // 获取锁成功
                log.debug("获取分布式锁成功，key: {}, value: {}，重试次数: {}", lockKey, lockFlag, totalRetryTimes - retryTimes);
            }
            // 获取锁失败
            log.debug("获取分布式锁失败。key: {}, value: {}，剩余重试次数：{}，睡眠时间：{}ms",
                    lockKey, lockFlag, retryTimes, retryIntervalMs
            );
            if (retryIntervalMs > 0) {
                try {
                    Thread.sleep(retryIntervalMs);
                } catch (InterruptedException ignored) {
                }
            }
        }
        // 重试次数用尽也没有获取锁
        if (lockFailedThrowException) {
            throw new BaseException(ApiInfo.SERVER_BUSY, "业务繁忙，请稍后重试");
        }
    }

    public void unlock(String lockKey, String lockFlag) {
        log.debug("释放分布式锁成功，key: {}, value: {}", lockKey, lockFlag);
        redisLockOperator.unlockLua(lockKey, lockFlag);
    }
}
