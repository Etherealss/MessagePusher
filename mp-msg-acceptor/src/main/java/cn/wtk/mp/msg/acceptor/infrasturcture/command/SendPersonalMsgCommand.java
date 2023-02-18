package cn.wtk.mp.msg.acceptor.infrasturcture.command;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023-02-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendPersonalMsgCommand extends SendMsgCommand {
    @NotNull
    Long senderId;

    @NotNull
    Long revrId;

    String relationTopic;
}
