package cn.wtk.mp.relation.domain.relation.sub;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubRelationVO {
    Long connectorId;
    Long subscriberId;
    String relationTopic;
}