package cn.wtk.mp.common.msg.entity;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023-02-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonalMsg extends PersistentMsg {
    Long senderId;
    Long rcvrId;
    String relationTopic;

    public PersonalMsg() {
        super.msgType = MsgType.PERSONAL;
    }

    @Override
    public Long getRcvrId() {
        return rcvrId;
    }
}
