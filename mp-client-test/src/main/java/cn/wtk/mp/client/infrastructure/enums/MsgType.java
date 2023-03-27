package cn.wtk.mp.client.infrastructure.enums;

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
    ACK(0, "ack"),
    PERSONAL(1, "私信"),
    GROUP(2, "群消息"),
    ;
    private final int code;
    private final String name;
}
