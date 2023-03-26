package cn.wtk.mp.msg.manager.domain.msg.store.group;

import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.manager.domain.msg.store.MsgEntity;
import cn.wtk.mp.msg.manager.infrasturcture.constant.MsgEntityFieldName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author wtk
 * @date 2023-02-12
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class GroupMsgEntity extends MsgEntity {

    @Field(MsgEntityFieldName.GROUP_ID)
    Long groupId;

    @Field(MsgEntityFieldName.MSG_TYPE)
    MsgType msgType = MsgType.GROUP;

    private void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }
}
