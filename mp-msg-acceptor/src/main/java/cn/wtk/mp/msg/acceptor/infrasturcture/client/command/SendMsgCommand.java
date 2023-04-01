package cn.wtk.mp.msg.acceptor.infrasturcture.client.command;

import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgHeader;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @Pattern(regexp = "^[a-zA-Z0-9_\\-/]{1,100}$")
    @NotBlank
    String msgTopic;

    Object payload;

    Object detail;

    /**
     * 是否需要持久化
     */
    Boolean needPersistent = true;

    /**
     * 是否需要进行消息推送
     */
    Boolean needPush = true;

    /**
     * 是否推送给发送者
     */
    Boolean needSendToMyself = true;

    @NotNull
    Long senderId;

    String senderIp;

    Integer senderPort;
}
