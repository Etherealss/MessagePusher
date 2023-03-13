package cn.wtk.mp.connect.infrastructure.client.converter;

import cn.wtk.mp.connect.domain.msg.connector.ConnectorTransferMsg;
import cn.wtk.mp.connect.domain.msg.connector.TransferMsg;
import cn.wtk.mp.connect.domain.msg.connector.device.DeviceTransferMsg;
import cn.wtk.mp.connect.infrastructure.client.command.MsgPushCommand;
import cn.wtk.mp.connect.infrastructure.client.command.msg.connector.ConnectorMsgPushCommand;
import cn.wtk.mp.connect.infrastructure.client.command.msg.device.DeviceMsgPushCommand;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author wtk
 * @date 2023-02-25
 */
@Mapper
public interface MsgConverter {

    TransferMsg toTransferMsg(MsgPushCommand command);
    List<ConnectorTransferMsg> toConnectorTransferMsgs(List<ConnectorMsgPushCommand> command);
    List<DeviceTransferMsg> toDeviceTransferMsgs(List<DeviceMsgPushCommand> command);
}
