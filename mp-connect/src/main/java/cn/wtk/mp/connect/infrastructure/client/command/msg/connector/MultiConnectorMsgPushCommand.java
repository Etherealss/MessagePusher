package cn.wtk.mp.connect.infrastructure.client.command.msg.connector;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author wtk
 * @date 2023/2/24
 */
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor
public class MultiConnectorMsgPushCommand {
    @Valid
    @NotEmpty
    List<ConnectorMsgPushCommand> msgs;
}
