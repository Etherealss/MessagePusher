package cn.wtk.mp.common.base.enums;

import cn.wtk.mp.common.base.exception.rest.EnumIllegalException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author wtk
 * @date 2022-04-21
 */
public interface BaseEnum {

    /**
     * 存储到数据库的枚举值
     * 注解的作用是在返回前端时传数字
     * @return
     */
    @JsonValue
    int getCode();

    /**
     * 用于显示的枚举名
     * @return
     */
    String getName();

    /**
     * 按枚举的code获取枚举实例
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    static <T extends BaseEnum> T fromCode(Class<T> enumType, int code) {
        for (T object : enumType.getEnumConstants()) {
            if (code == object.getCode()) {
                return object;
            }
        }
        throw new EnumIllegalException(enumType, code);
    }

    /**
     * 按枚举的name获取枚举实例
     */
    static <T extends BaseEnum> T fromName(Class<T> enumType, String name) {
        for (T object : enumType.getEnumConstants()) {
            if (name.equals(object.getName())) {
                return object;
            }
        }
        throw new EnumIllegalException(enumType, name);
    }
}
