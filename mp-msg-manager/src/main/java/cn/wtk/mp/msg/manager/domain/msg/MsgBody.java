package cn.wtk.mp.msg.manager.domain.msg;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * @author wtk
 * @date 2023-03-20
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MsgBody {
    Long msgId;
    Long appId;
    Long senderId;
    String msgTopic;
    MsgType msgType;
    Date sendTime;
    Date saveTime;
    Object payload;
    Object detail;
    Long rcvrId;
    Long groupId;
}
