package cn.wtk.mp.msg.manager.domain.msg.store.personal;

import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.manager.domain.msg.store.IMsgService;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.MsgConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class PersonalMsgService implements IMsgService<PersonalMsg> {

    private static final MsgType SUPPORT = MsgType.PERSONAL;

    private final MongoTemplate mongoTemplate;
    private final MsgConverter msgConverter;

    @Override
    public MsgType getSupport() {
        return PersonalMsgService.SUPPORT;
    }
    @Override
    public void insert(PersonalMsg msg) {
        PersonalMsgEntity entity = msgConverter.toEntity(msg);
        mongoTemplate.insert(entity);
    }
}
