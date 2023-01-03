package cn.wtk.mp.common.database.jpa;

import cn.wtk.mp.common.database.pojo.entity.IdentifiedEntity;
import cn.wtk.mp.common.database.uid.UidGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

/**
 * @author wtk
 * @date 2023-01-02
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class IdEventListener extends AbstractMongoEventListener<IdentifiedEntity> {

    private final UidGenerator uidGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<IdentifiedEntity> event) {
        final IdentifiedEntity entity = event.getSource();
        ReflectionUtils.doWithFields(entity.getClass(), field -> {
            if (field.isAnnotationPresent(MongoId.class)) {
                ReflectionUtils.makeAccessible(field);
                Object value = field.get(entity);
                if (value == null) {
                    long id = uidGenerator.nextId();
                    log.info("自动设置id：{}", id);
                    field.set(entity, id);
                }
            }
        });
        super.onBeforeConvert(event);
    }
}
