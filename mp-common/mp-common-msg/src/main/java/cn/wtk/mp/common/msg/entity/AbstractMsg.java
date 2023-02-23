package cn.wtk.mp.common.msg.entity;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wtk
 * @date 2023/2/10
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractMsg implements Serializable {

    Long msgId;

    Long appId;

    MsgType msgType;

    Date sendTime;

    /**
     * 是否需要持久化
     */
    Boolean persistent;
}
