package cn.wtk.mp.msg.acceptor.infrasturcture.client.converter;

import cn.wtk.mp.common.base.enums.MapperComponentModel;
import cn.wtk.mp.common.msg.dto.mq.ManagerMsgBody;
import cn.wtk.mp.common.msg.dto.mq.ManagerMsgHeader;
import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgBody;
import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgHeader;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.command.SendGroupMsgCommand;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.command.SendMsgCommand;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.command.SendPersonalMsgCommand;
import org.mapstruct.Mapper;

/**
 * @author wtk
 * @date 2023/2/21
 */
@Mapper(componentModel = MapperComponentModel.SPRING)
public interface MsgConverter {
    MsgBody toMsg(SendPersonalMsgCommand command);
    MsgBody toMsg(SendGroupMsgCommand command);
    MsgHeader toMsgHeader(SendMsgCommand command);
    ManagerMsgHeader toMsgHeader(MsgHeader msgHeader);
    ManagerMsgBody toMsgBody(MsgBody msgBody);
}
