package cn.wtk.mp.relation.domain.relation.group;

import cn.wtk.mp.common.database.pojo.entity.BaseEntity;
import cn.wtk.mp.common.database.pojo.entity.IdentifierGetter;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/14
 */
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("mp_relation_group")
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GroupRelationEntity extends BaseEntity implements IdentifierGetter {
    /**
     * 群组 ID
     */
    Long groupId;

    /**
     * 群组归属的 APP ID
     */
    Long appId;

    /**
     * 群组的业务标识符
     */
    String groupTopic;

    /**
     * 群主，创建者
     */
    Long creator;

    /**
     * 群成员
     */
    List<Long> memberIds;

    @Override
    public Long getId() {
        return groupId;
    }
}
