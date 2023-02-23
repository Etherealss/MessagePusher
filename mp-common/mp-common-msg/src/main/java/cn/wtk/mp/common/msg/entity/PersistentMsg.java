package cn.wtk.mp.common.msg.entity;

import cn.wtk.mp.common.msg.enums.MsgTransferStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * 需要持久化的消息
 * @author wtk
 * @date 2023-02-11
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class PersistentMsg extends Msg {
    MsgTransferStatus transferStatus;
    Date saveTime;
    Date recvTime;
}
