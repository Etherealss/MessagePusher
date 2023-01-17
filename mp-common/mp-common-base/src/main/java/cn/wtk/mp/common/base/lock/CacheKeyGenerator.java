package cn.wtk.mp.common.base.lock;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author wang tengkun
 * @date 2022/2/26
 */
public interface CacheKeyGenerator {

    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint pjp);
}
