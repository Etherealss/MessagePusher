package cn.wtk.mp.common.database.pojo.entity;

import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * @author wtk
 * @date 2023/2/14
 */
public interface IdentifierGetter {
    @MongoId(FieldType.INT64)
    Long getId();
}
