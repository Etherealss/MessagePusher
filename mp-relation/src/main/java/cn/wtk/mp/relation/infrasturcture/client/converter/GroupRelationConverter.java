package cn.wtk.mp.relation.infrasturcture.client.converter;

import cn.wtk.mp.common.base.enums.MapperComponentModel;
import cn.wtk.mp.relation.domain.group.relation.GroupRelationEntity;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.CreateGroupCommand;
import org.mapstruct.Mapper;

/**
 * @author wtk
 * @date 2023/2/15
 */
@Mapper(componentModel = MapperComponentModel.SPRING)
public interface GroupRelationConverter {
    GroupRelationEntity toEntity(CreateGroupCommand command);
}
