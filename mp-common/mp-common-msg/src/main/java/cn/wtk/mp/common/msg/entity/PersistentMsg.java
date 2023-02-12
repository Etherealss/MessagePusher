package cn.wtk.mp.common.msg.entity;

import cn.wtk.mp.common.msg.enums.MsgTransferStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * 需要持久化的消息
 * @author wtk
 * @date 2023-02-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class PersistentMsg extends Msg {
    MsgTransferStatus transferStatus;
    Date saveTime;
    Date recvTime;

    @Override
    public boolean isPersistentMsg() {
        return true;
    }
}
