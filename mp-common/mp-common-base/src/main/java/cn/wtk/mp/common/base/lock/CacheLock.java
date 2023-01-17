package cn.wtk.mp.common.base.lock;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wang tengkun
 * @date 2022/2/26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheLock {

    /**
     * redis 锁key，会自动加上前缀 ${spring.cache.redis.key-prefix}
     * @return redis 锁key名
     */
    String key() default "";

    /**
     * 获取锁失败时的重试间隔，即 {@link Thread#sleep(long) } 的时长
     * @return 获取锁失败时的睡眠时间（毫秒），默认5000毫秒
     */
    long retryIntervalMs() default 5000;

    /**
     * 获取锁失败时的重试次数
     * @return 获取锁失败时的重试次数，默认为0，不重试
     */
    int retryTimes() default 0;

    /**
     * 过期秒数,默认为10秒
     * @return 锁的过期时间
     */
    int expire() default 10;

    /**
     * 超时时间单位
     * @return 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * <p>Key的分隔符（默认 :）</p>
     * <p>生成的Key：N:SO1008:500</p>
     * @return String
     */
    String delimiter() default ":";

    boolean lockFailedThrowException() default true;
}
