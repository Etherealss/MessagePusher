package cn.wtk.mp.relation.domain.connector.relation;


import cn.wtk.mp.common.database.pojo.entity.BaseEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

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
public class SubRelationEntity extends BaseEntity  {
    /**
     * connector ID
     */
    @MongoId(FieldType.INT64)
    @Field(CONNECTOR_ID)
    Long connectorId;

    /**
     * 所有订阅该 connector 的人，可以类比于"所有粉丝"
     */
    @Field(RELATIONS)
    List<SubRelationItem> relations;

    public static final String CONNECTOR_ID = "_id";
    public static final String RELATIONS = "relations";

    public Long getId() {
        return connectorId;
    }

}
