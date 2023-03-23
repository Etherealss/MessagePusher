package cn.wtk.mp.connect.infrastructure.client.converter;

import cn.wtk.mp.common.base.enums.MapperComponentModel;
import cn.wtk.mp.connect.domain.msg.connector.TransferMsg;
import cn.wtk.mp.connect.infrastructure.client.command.MsgPushCommand;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author wtk
 * @date 2023-02-25
 */
@Mapper(componentModel = MapperComponentModel.SPRING)
public interface MsgConverter {
    TransferMsg toTransferMsg(MsgPushCommand command);
    List<TransferMsg> toConnectorTransferMsgs(List<MsgPushCommand> command);
}
