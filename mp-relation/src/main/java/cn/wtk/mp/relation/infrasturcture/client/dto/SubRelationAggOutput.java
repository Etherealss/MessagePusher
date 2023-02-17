package cn.wtk.mp.relation.infrasturcture.client.dto;

import cn.wtk.mp.relation.domain.relation.sub.SubRelationEntity;
import cn.wtk.mp.relation.domain.relation.sub.SubRelationItem;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author wtk
 * @date 2023/2/15
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class SubRelationAggOutput {
    @Field(SubRelationEntity.RELATIONS)
    SubRelationItem relations;

}
