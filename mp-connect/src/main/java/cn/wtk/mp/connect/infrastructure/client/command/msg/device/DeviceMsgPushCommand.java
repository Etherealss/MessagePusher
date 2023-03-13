package cn.wtk.mp.connect.infrastructure.client.command.msg.device;

import cn.wtk.mp.connect.infrastructure.client.command.MsgPushCommand;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author wtk
 * @date 2023/2/24
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor
public class DeviceMsgPushCommand extends MsgPushCommand {
    @NotEmpty
    List<Long> deivceRcvrIds;
}
