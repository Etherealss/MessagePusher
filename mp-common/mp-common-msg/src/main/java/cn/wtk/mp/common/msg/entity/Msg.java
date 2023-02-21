package cn.wtk.mp.common.msg.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023/2/10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Msg extends AbstractMsg {
    String msgTopic;
    Object data;

    public abstract Long getRcvrId();
}
