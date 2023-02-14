package cn.wtk.mp.relation.domain.relation.sub;


import cn.wtk.mp.common.database.pojo.entity.BaseEntity;
import cn.wtk.mp.common.database.pojo.entity.IdentifierGetter;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 订阅关系：单向，一对一
 * A 要给 B 发消息，那么首先 B 要订阅 A，这样 B 才可以收到 A 的消息
 * @author wtk
 * @date 2023/2/14
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("mp_relation_sub")
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SubRelationEntity extends BaseEntity implements IdentifierGetter {
    /**
     * connector ID
     */
    Long connectorId;

    /**
     * 所有订阅该 connector 的人，可以类比于"所有粉丝"
     */
    List<SubRelationItem> subscribers;

    @Override
    public Long getId() {
        return connectorId;
    }
}
