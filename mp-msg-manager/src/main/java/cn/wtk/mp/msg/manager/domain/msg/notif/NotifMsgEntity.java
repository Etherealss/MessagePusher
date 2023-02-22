package cn.wtk.mp.msg.manager.domain.msg.notif;

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
public class NotifMsgEntity extends PersistentMsgEntity {
    @Field(MsgEntityFieldName.RCVR_ID)
    Long rcvrId;

    @Field(MsgEntityFieldName.MSG_TYPE)
    MsgType msgType = MsgType.NOTIF;

    private void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }
}
