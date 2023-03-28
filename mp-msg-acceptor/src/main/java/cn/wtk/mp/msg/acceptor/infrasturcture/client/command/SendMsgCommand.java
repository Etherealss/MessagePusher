package cn.wtk.mp.msg.acceptor.infrasturcture.client.command;

import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgHeader;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * @author wtk
 * @date 2023-02-12
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendMsgCommand {
    /**
     * {@link MsgHeader#getTempId()}
     */
    @NotNull
    UUID tempId;

    /**
     * {@link MsgHeader#getPreMsgTempId()}
     */
    UUID preMsgTempId;

    /**
     * {@link MsgHeader#getPreMsgSendTime()}
     */
    Date preMsgSendTime;

    @NotNull
    Date sendTime;

    @NotEmpty
    String msgTopic;

    Object payload;

    Object detail;

    @NotNull
    Boolean needPersistent;

    @NotNull
    Long senderId;
}
