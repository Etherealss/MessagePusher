package cn.wtk.mp.common.base.lock;

import java.lang.annotation.*;

/**
 * @author wang tengkun
 * @date 2022/2/26
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {

    /**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
}
