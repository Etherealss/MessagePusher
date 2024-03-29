package cn.wtk.mp.relation.domain.connector.relation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author wtk
 * @date 2023/2/14
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubRelationItem {
    /**
     * 订阅者 ID
     */
    @Field(SUBR_ID)
    Long subrId;

    /**
     * 订阅类型，可以理解为关系类型，如"好友关系"、"同学关系"
     */
    @Field(RELATION_TOPIC)
    String relationTopic;

    public static final String SUBR_ID = "subrId";
    public static final String RELATION_TOPIC = "relationTopic";
}
