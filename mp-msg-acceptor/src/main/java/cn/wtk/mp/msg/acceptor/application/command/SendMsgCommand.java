package cn.wtk.mp.msg.acceptor.application.command;

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
     * 用于去重的临时id
     */
    @NotNull
    UUID tempMsgId;

    @NotNull
    Date sendTime;

    @NotNull
    Boolean resend;

    @NotEmpty
    String msgTopic;

    @NotNull
    Object data;
}
