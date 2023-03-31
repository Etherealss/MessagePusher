package cn.wtk.mp.common.msg.dto.mq;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023-03-20
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerMsgHeader {

    @NotNull MsgType msgType;
    @NotNull Boolean needPersistent;
    @NotNull Boolean needRelationVerify;
    @NotNull Boolean needPush;
    @NotNull Long senderId;
    String relationTopic;
    Long rcvrId;
    Long groupId;
}
