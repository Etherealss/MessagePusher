package cn.wtk.mp.common.msg.entity;

import cn.wtk.mp.common.msg.enums.MsgTransferStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023-02-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AckMsg extends AbstractMsg {
    Long ackMsgId;
    MsgTransferStatus ackType;
}
