package cn.wtk.mp.common.msg.dto.mq;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author wtk
 * @date 2023-03-20
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerMsgBody {
    @NotNull Long msgId;
    @NotNull Long appId;
    @NotNull Long senderId;
    @NotBlank String msgTopic;
    @NotNull MsgType msgType;
    @NotNull Date sendTime;
    Object payload;
    Object detail;
    Long rcvrId;
    Long groupId;
}
