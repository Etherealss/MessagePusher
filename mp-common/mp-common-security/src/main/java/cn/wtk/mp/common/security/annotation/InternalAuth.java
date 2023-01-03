package cn.wtk.mp.common.security.annotation;

import java.lang.annotation.*;

/**
 * 认证是否为系统内部的请求
 * @author wtk
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InternalAuth {
    /**
     * 是否校验用户信息
     */
    boolean isUser() default false;
}