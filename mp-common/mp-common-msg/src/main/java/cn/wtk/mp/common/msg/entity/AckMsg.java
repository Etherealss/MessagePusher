package cn.wtk.mp.common.msg.entity;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023-02-11
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AckMsg {
    Long msgId;
    MsgType msgType = MsgType.ACK;
}
