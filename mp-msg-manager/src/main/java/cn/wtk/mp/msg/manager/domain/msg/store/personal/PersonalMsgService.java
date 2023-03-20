package cn.wtk.mp.msg.manager.domain.msg.store.personal;

import cn.wtk.mp.msg.manager.domain.msg.MsgBody;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.PersonalMsgConverter;
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
public class PersonalMsgService {

    private final MongoTemplate mongoTemplate;
    private final PersonalMsgConverter msgConverter;

    public void insert(MsgBody msg) {
        PersonalMsgEntity entities = msgConverter.toEntity(msg);
        mongoTemplate.insert(entities);
    }
}
