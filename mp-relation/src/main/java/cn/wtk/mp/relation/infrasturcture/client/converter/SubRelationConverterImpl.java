package cn.wtk.mp.relation.infrasturcture.client.converter;

import cn.wtk.mp.relation.domain.relation.sub.SubRelationEntity;
import cn.wtk.mp.relation.infrasturcture.client.dto.SubRelationsDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/15
 */
@Component
@Slf4j
public class SubRelationConverterImpl implements SubRelationConverter {
    @Override
    public SubRelationsDTO toDTO(List<SubRelationEntity> entities) {
        return null;
    }
}
