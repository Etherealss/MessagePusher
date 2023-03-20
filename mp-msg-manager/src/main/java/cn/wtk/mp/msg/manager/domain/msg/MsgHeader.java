package cn.wtk.mp.msg.manager.domain.msg;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

/**
 * @author wtk
 * @date 2023-03-20
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MsgHeader {
    UUID tempId;
    UUID preTempId;
    Date preSendTime;
    MsgType msgType;
    Boolean needPersistent;
    Boolean needRelationVerify;
    String relationTopic;
    Long senderId;
    Long rcvrId;
    Long groupId;
}
