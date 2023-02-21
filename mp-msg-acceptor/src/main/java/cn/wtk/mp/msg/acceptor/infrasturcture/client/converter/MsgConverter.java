package cn.wtk.mp.msg.acceptor.infrasturcture.client.converter;

import cn.wtk.mp.common.base.enums.MapperComponentModel;
import cn.wtk.mp.common.msg.entity.GroupMsg;
import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.command.SendGroupMsgCommand;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.command.SendPersonalMsgCommand;
import org.mapstruct.Mapper;

/**
 * @author wtk
 * @date 2023/2/21
 */
@Mapper(componentModel = MapperComponentModel.SPRING)
public interface MsgConverter {
    PersonalMsg toMsg(SendPersonalMsgCommand command);
    GroupMsg toMsg(SendGroupMsgCommand command);
}
