package cn.wtk.mp.common.base.pojo;


import cn.wtk.mp.common.base.exception.internal.BugException;
import org.springframework.beans.BeanUtils;
import cn.wtk.mp.common.base.utils.ReflectUtil;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * @author wtk
 * @date 2022-04-25
 */
public interface Converter<T> {

    /**
     * 将当前对象转为指定的泛型类
     * @return 对应的泛型类
     */
    @SuppressWarnings("unchecked")
    default T convert() {
        // 获取泛型
        ParameterizedType currentType = ReflectUtil.getParameterizedType(Converter.class, this.getClass());
        Objects.requireNonNull(currentType, "无法获取泛型");

        Class<T> targetClass = (Class<T>) currentType.getActualTypeArguments()[0];
        try {
            T t = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(this, t);
            return t;
        } catch (Exception e) {
            throw new BugException("无法获取" + targetClass.getSimpleName() + "的无参构造器：" + e.getMessage());
        }
    }

    /**
     * 将当前对象转为任意对象
     * @param targetClass 任意对象的class
     * @param <OTHER>
     * @return
     */
    default <OTHER> OTHER convert(Class<OTHER> targetClass) {
        try {
            OTHER target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(this, target);
            return target;
        } catch (Exception e) {
            throw new BugException("无法获取" + targetClass.getSimpleName() + "的无参构造器：" + e.getMessage());
        }
    }

    /**
     * 通过传入的参数更新当前对象
     * @param target 需要更新的数据值
     */
    default void update(T target) {
        BeanUtils.copyProperties(this, target);
    }
}

