package cn.wtk.mp.relation.infrasturcture.client.converter;

import cn.wtk.mp.relation.domain.relation.sub.SubRelationEntity;
import cn.wtk.mp.relation.infrasturcture.client.dto.SubRelationsDTO;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/15
 */
public interface SubRelationConverter {
    SubRelationsDTO toDTO(List<SubRelationEntity> entities);
}
