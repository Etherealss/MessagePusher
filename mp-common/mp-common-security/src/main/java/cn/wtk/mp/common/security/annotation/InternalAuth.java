package cn.wtk.mp.common.security.annotation;

import cn.wtk.mp.common.security.aspect.PreAuthAspect;

import java.lang.annotation.*;

/**
 * 请求认证
 * @author wtk
 * @see PreAuthAspect
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InternalAuth {
    /**
     * 是否校验 serverToken
     */
    boolean serv() default true;
}