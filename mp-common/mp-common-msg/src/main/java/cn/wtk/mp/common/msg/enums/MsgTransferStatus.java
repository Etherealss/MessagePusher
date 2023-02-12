package cn.wtk.mp.common.msg.enums;

import cn.wtk.mp.common.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wtk
 * @date 2023-02-11
 */
@AllArgsConstructor
@Getter
public enum MsgTransferStatus implements BaseEnum {
    SENDING(0, "发送中"),
    SENT(1, "已发送"),
    REND(2, "已读"),
    FAIL(-1, "发送失败"),
    REJECT(-2, "拒绝发送"),
    ;
    private final int code;
    private final String name;
}
