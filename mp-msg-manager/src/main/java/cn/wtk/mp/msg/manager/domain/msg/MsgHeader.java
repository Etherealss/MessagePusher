package cn.wtk.mp.msg.manager.domain.msg;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023-03-20
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MsgHeader {
    MsgType msgType;
    Boolean needPersistent;
    Boolean needRelationVerify;
    Boolean needPush;
    String relationTopic;
    Long senderId;
    Long rcvrId;
    Long groupId;
}
