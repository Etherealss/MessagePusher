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
    FAIL(-1, "发送失败"),
    REJECT(-2, "拒绝发送"),
    REND(0, "已读"),
    SENDING(1, "发送中"),
    SENT(2, "已发送"),
    ;
    private final int code;
    private final String name;

    public static int getUnreadStatus() {
        return SENDING.getCode();
    }

    /**
     * 可读消息的状态
     * @return
     */
    public static int getReadableStatus() {
        return REND.getCode();
    }
}
