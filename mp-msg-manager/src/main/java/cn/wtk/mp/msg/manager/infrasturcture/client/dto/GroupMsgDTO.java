package cn.wtk.mp.msg.manager.infrasturcture.client.dto;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * @author wtk
 * @date 2023-03-26
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupMsgDTO {
    Long msgId;
    Long appId;
    Long senderId;
    String msgTopic;
    MsgType msgType;
    Date sendTime;
    Object payload;
    Object detail;
    Long groupId;
}
