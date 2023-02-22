package cn.wtk.mp.common.msg.entity;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * @author wtk
 * @date 2023/2/10
 */
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractMsg {
    Long msgId;
    Long appId;
    MsgType msgType;
    Date sendTime;

    public boolean isPersistentMsg() {
        return false;
    }
}
