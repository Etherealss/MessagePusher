package cn.wtk.mp.common.database.audit;

import cn.wtk.mp.common.database.pojo.entity.BaseEntity;
import cn.wtk.mp.common.base.uid.UidGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.Date;

/**
 * MongoDB的 @Id 和 @CreateDate 有冲突，
 * 如果使用了自定义 @Id，那么 spring-data-mongo 通过调用 isNew() 认为在 mongo 中已经存在，
 * 导致 @CreatedDate 不会生效，而 @LastModifiedDate 会生效
 * 所以此处手动设置 CreateDate
 * @author wtk
 * @date 2023-01-02
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuditingEventListener extends AbstractMongoEventListener<BaseEntity> {

    private final UidGenerator uidGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<BaseEntity> event) {
        final BaseEntity entity = event.getSource();
        ReflectionUtils.doWithFields(entity.getClass(), field -> {
            if (field.isAnnotationPresent(CreatedDate.class)) {
                ReflectionUtils.makeAccessible(field);
                Object value = field.get(entity);
                if (value == null) {
                    field.set(entity, new Date());
                }
            }
        });
        super.onBeforeConvert(event);
    }
}
