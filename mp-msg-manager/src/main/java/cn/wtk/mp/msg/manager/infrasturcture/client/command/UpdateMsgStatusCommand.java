package cn.wtk.mp.msg.manager.infrasturcture.client.command;

import cn.wtk.mp.common.msg.enums.MsgTransferStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMsgStatusCommand {
    @NotNull
    MsgTransferStatus status;
}
