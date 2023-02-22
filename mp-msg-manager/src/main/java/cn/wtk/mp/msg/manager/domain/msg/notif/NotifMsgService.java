package cn.wtk.mp.msg.manager.domain.msg.notif;

import cn.wtk.mp.common.msg.entity.NotifMsg;
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
public class NotifMsgService {

    private final MongoTemplate mongoTemplate;
    private final MsgConverter msgConverter;

    public void consume(NotifMsg msg) {
        NotifMsgEntity entity = msgConverter.toEntity(msg);
        mongoTemplate.insert(entity);
    }
}
