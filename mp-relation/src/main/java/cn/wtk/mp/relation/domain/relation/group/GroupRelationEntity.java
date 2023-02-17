package cn.wtk.mp.relation.domain.relation.group;

import cn.wtk.mp.common.database.pojo.entity.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

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
public class GroupRelationEntity extends BaseEntity {

    /**
     * 群组 ID
     */
    @MongoId
    @Field(GROUP_ID)
    Long groupId;

    /**
     * 群组归属的 APP ID
     */
    @Field(APP_ID)
    Long appId;

    /**
     * 群组的业务标识符
     */
    @Field(GROUP_TOPIC)
    String groupTopic;

    /**
     * 群主，创建者
     */
    @Field(CREATOR_ID)
    Long creatorId;

    /**
     * 群成员
     */
    @Field(MEMBER_IDS)
    List<Long> memberIds;

    public static final String GROUP_ID = "_id";
    public static final String APP_ID = "appId";
    public static final String GROUP_TOPIC = "groupTopic";
    public static final String CREATOR_ID = "creatorId";
    public static final String MEMBER_IDS = "memberIds";
}
