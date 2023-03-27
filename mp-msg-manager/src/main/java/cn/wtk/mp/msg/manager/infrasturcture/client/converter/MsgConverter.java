package cn.wtk.mp.msg.manager.infrasturcture.client.converter;

import cn.wtk.mp.common.base.enums.MapperComponentModel;
import cn.wtk.mp.common.msg.entity.GroupMsg;
import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.msg.manager.domain.msg.MsgBody;
import cn.wtk.mp.msg.manager.domain.msg.store.MsgEntity;
import cn.wtk.mp.msg.manager.infrasturcture.client.dto.GroupMsgDTO;
import cn.wtk.mp.msg.manager.infrasturcture.client.dto.MsgDTO;
import cn.wtk.mp.msg.manager.infrasturcture.client.dto.PersonalMsgDTO;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.command.MsgPushCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Mapper(componentModel = MapperComponentModel.SPRING)
public interface MsgConverter {
    MsgEntity toEntity(MsgBody msg);
    List<MsgEntity> toEntities(List<MsgBody> msg);

    MsgPushCommand toPushDTO(GroupMsg msg);
    MsgPushCommand toPushDTO(PersonalMsg msg);
    MsgPushCommand toPushCommand(MsgBody msgBody);

    MsgDTO toDto(MsgEntity entity);
    List<MsgDTO> toDTOs(List<MsgEntity> entities);

    PersonalMsgDTO toPersonalDTO(MsgEntity entity);
    List<PersonalMsgDTO> toPersonalDTOs(List<MsgEntity> entities);

    @Mapping(source = "rcvrId", target = "groupId")
    GroupMsgDTO toGroupDTO(MsgEntity entity);
    @Mapping(source = "rcvrId", target = "groupId")
    List<GroupMsgDTO> toGroupDTOs(List<MsgEntity> entities);
}
