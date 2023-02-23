package cn.wtk.mp.msg.manager.infrasturcture.client.dto;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * 消息推送对象，用于：msg-manager -> connect
 * @author wtk
 * @date 2023/2/23
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MsgPushDTO {
    Long msgId;
    Long appId;
    MsgType msgType;
    String msgTopic;
    Object data;
    Date sendTime;
    Date saveTime;
    Long senderId;
    Long rcvrId;
}
