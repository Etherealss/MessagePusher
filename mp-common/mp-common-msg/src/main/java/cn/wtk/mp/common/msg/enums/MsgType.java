package cn.wtk.mp.common.msg.enums;

import cn.wtk.mp.common.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wtk
 * @date 2023-02-11
 */
@Getter
@AllArgsConstructor
public enum MsgType implements BaseEnum {
    PERSONAL(0, "私信"),
    GROUP(1, "群消息"),
    NOTIF(2, "通知"),
    EVENT(4, "事件"),
    ACK(5, "ack"),
    ;
    private final int code;
    private final String name;
}
