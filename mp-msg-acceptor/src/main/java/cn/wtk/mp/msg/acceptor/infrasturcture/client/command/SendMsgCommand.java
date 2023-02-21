package cn.wtk.mp.msg.acceptor.infrasturcture.client.command;

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
     * 用于去重的临时 ID
     */
    @NotNull
    UUID tempMsgId;

    /**
     * 上一条消息的 ID，用于保证消息有序。
     * 如果为空则视为首条消息，不检查有序性
     */
    UUID preTempMsgId;

    /**
     * 上一条消息的发送时间。
     * 如果发送时间超过服务器配置的时间则认为上一条消息已成功发送，不检查有序性
     * 如果发送时间为空，同样不检查有序性（不管 preTempMsgId 的值）
     */
    Date preMsgSendTime;

    @NotNull
    Date sendTime;

    @NotNull
    Boolean resend;

    @NotEmpty
    String msgTopic;

    @NotNull
    Object data;
}
