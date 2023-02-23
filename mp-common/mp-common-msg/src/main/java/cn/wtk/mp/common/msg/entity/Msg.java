package cn.wtk.mp.common.msg.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023/2/10
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Msg extends AbstractMsg {
    String msgTopic;
    Object data;

    public abstract Long getRcvrId();
}
