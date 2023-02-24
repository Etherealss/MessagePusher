package cn.wtk.mp.msg.manager.infrasturcture.client.converter;

import cn.wtk.mp.common.base.enums.MapperComponentModel;
import cn.wtk.mp.common.msg.entity.GroupMsg;
import cn.wtk.mp.common.msg.entity.NotifMsg;
import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.msg.manager.domain.msg.store.group.GroupMsgEntity;
import cn.wtk.mp.msg.manager.domain.msg.store.notif.NotifMsgEntity;
import cn.wtk.mp.msg.manager.domain.msg.store.personal.PersonalMsgEntity;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.command.MsgPushCommand;
import org.mapstruct.Mapper;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Mapper(componentModel = MapperComponentModel.SPRING)
public interface MsgConverter {
    GroupMsgEntity toEntity(GroupMsg groupMsg);
    PersonalMsgEntity toEntity(PersonalMsg personalMsg);
    NotifMsgEntity toEntity(NotifMsg notifMsg);

    MsgPushCommand toPushDTO(PersonalMsg msg);
    MsgPushCommand toPushDTO(GroupMsg msg);
    MsgPushCommand toPushDTO(NotifMsg msg);
}
