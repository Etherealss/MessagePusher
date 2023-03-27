package cn.wtk.mp.common.msg.entity;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023-02-12
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupMsg extends PersistentMsg {
    Long senderId;
    Long groupId;

    public GroupMsg() {
        super.msgType = MsgType.GROUP;
    }

    @Override
    public Long getRcvrId() {
        return groupId;
    }
}
