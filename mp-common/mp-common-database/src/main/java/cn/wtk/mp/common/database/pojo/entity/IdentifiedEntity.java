package cn.wtk.mp.common.database.pojo.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * @author wang tengkun
 * @date 2022/2/23
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public abstract class IdentifiedEntity extends BaseEntity implements IdentifierGetter {
    @MongoId(FieldType.INT64)
    protected Long id;
}
