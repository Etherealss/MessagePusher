package cn.wtk.mp.msg.manager.domain.msg.store.personal;

import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.manager.domain.msg.store.PersistentMsgEntity;
import cn.wtk.mp.msg.manager.infrasturcture.constant.MsgEntityFieldName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author wtk
 * @date 2023-02-12
 */
@Document("mp-msg-personal")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PersonalMsgEntity extends PersistentMsgEntity {
    @Field(MsgEntityFieldName.SENDER_ID)
    Long senderId;

    @Field(MsgEntityFieldName.RCVR_ID)
    Long rcvrId;

    @Field(MsgEntityFieldName.MSG_TYPE)
    MsgType msgType = MsgType.PERSONAL;

    private void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }
}
