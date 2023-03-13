package cn.wtk.mp.connect.domain.msg.connector;

import cn.wtk.mp.connect.infrastructure.enums.MsgType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * @author wtk
 * @date 2023-02-25
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class TransferMsg {
    Long msgId;
    Long appId;
    MsgType msgType;
    String msgTopic;
    Object data;
    Date sendTime;
    Date saveTime;
    Long senderId;
}
