package cn.wtk.mp.msg.manager.infrasturcture.client.converter;

import cn.wtk.mp.common.base.enums.MapperComponentModel;
import cn.wtk.mp.msg.manager.domain.msg.MsgBody;
import cn.wtk.mp.msg.manager.infrasturcture.client.dto.MsgDTO;
import org.mapstruct.Mapper;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Mapper(componentModel = MapperComponentModel.SPRING)
public interface MsgConverter {
    MsgDTO toDto(MsgBody personalMsg);
}
