package cn.wtk.mp.msg.manager.infrasturcture.client.converter;

import cn.wtk.mp.common.base.enums.MapperComponentModel;
import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.msg.manager.domain.msg.MsgBody;
import cn.wtk.mp.msg.manager.domain.msg.store.personal.PersonalMsgEntity;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.command.MsgPushCommand;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Mapper(componentModel = MapperComponentModel.SPRING)
public interface PersonalMsgConverter {
    PersonalMsgEntity toEntity(MsgBody personalMsg);
    List<PersonalMsgEntity> toEntities(List<MsgBody> personalMsg);
    MsgPushCommand toPushDTO(PersonalMsg msg);
}
