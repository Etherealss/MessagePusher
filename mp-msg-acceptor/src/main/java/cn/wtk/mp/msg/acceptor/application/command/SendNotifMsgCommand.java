package cn.wtk.mp.msg.acceptor.application.command;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023-02-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendNotifMsgCommand extends SendMsgCommand {
    Long revrId;
}
