package cn.wtk.mp.msg.manager.domain.msg.store.group;

import cn.wtk.mp.msg.manager.domain.msg.MsgBody;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.GroupMsgConverter;
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
public class GroupMsgService {

    private final MongoTemplate mongoTemplate;
    private final GroupMsgConverter msgConverter;

    public void insert(MsgBody msg) {
        GroupMsgEntity entity = msgConverter.toGroupEntity(msg);
        mongoTemplate.insert(entity);
    }
}
