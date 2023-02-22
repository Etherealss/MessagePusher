package cn.wtk.mp.msg.manager.domain.msg.group;

import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.manager.domain.msg.PersistentMsgEntity;
import cn.wtk.mp.msg.manager.infrasturcture.constant.MsgEntityFieldName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author wtk
 * @date 2023-02-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupMsgEntity extends PersistentMsgEntity {
    @Field(MsgEntityFieldName.SENDER_ID)
    Long senderId;

    @Field(MsgEntityFieldName.GROUP_ID)
    Long groupId;

    @Field(MsgEntityFieldName.MSG_TYPE)
    MsgType msgType = MsgType.GROUP;

    private void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }
}
