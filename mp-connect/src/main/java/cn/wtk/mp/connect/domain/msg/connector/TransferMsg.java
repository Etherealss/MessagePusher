package cn.wtk.mp.connect.domain.msg.connector;

import cn.wtk.mp.connect.infrastructure.enums.MsgType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

/**
 * @author wtk
 * @date 2023-02-25
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferMsg {
    Long msgId;
    Long appId;
    MsgType msgType;
    String msgTopic;
    List<Long> rcvrIds;
    Object payload;
    Date sendTime;
    Date saveTime;
    Long senderId;
}
