package cn.wtk.mp.common.security.aspect;


import cn.wtk.mp.common.security.service.auth.IPreAuthHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class PreAuthAspect {

    private final List<IPreAuthHandler> preAuthHandlers;

    /**
     * @param joinPoint 切面对象
     * @return 底层方法执行后的返回值
     * @throws Throwable 底层方法抛出的异常
     */
    @Around("@annotation(cn.wtk.mp.common.security.annotation.InternalAuth)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 注解鉴权
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        for (IPreAuthHandler preAuthHandler : preAuthHandlers) {
            if (preAuthHandler.checkNeedAuth(method)) {
                preAuthHandler.doAuth(method);
            }
        }
        // 执行原有逻辑
        return joinPoint.proceed();
    }


}
