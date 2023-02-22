package cn.wtk.mp.msg.acceptor.infrasturcture.client.command;

import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgHandlerSpec;
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
     * {@link MsgHandlerSpec#getTempId()}
     */
    @NotNull
    UUID tempMsgId;

    /**
     * {@link MsgHandlerSpec#getPreMsgTempId()} ()}
     */
    UUID preTempMsgId;

    /**
     * {@link MsgHandlerSpec#getPreMsgSendTime()} ()} ()}
     */
    Date preMsgSendTime;

    @NotNull
    Date sendTime;

    @NotEmpty
    String msgTopic;

    @NotNull
    Object data;
}
