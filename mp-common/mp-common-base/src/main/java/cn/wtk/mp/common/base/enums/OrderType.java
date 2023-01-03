package cn.wtk.mp.common.base.enums;


import cn.wtk.mp.common.base.exception.rest.EnumIllegalException;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 排序方式
 * @author wtk
 * @date 2022-04-26
 */
@AllArgsConstructor
@Getter
public enum OrderType implements BaseEnum {
    NONE(0, "none"),
    LIKE(1, "like"),
    TIME(2, "time"),
    ;
    private final int code;
    private final String name;

//    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OrderType fromName(@JsonProperty("name") Object name) {
        for (OrderType object : OrderType.class.getEnumConstants()) {
            if (name.equals(object.getName())) {
                return object;
            }
        }
        throw new EnumIllegalException(OrderType.class, name);
    }
}
