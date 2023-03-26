package cn.wtk.mp.msg.manager.domain.msg.store;

import cn.wtk.mp.common.msg.enums.MsgTransferStatus;
import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.manager.domain.msg.ManageMsg;
import cn.wtk.mp.msg.manager.domain.msg.store.group.GroupMsgService;
import cn.wtk.mp.msg.manager.domain.msg.store.personal.PersonalMsgService;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.MsgConverter;
import cn.wtk.mp.msg.manager.infrasturcture.constant.MsgEntityFieldName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MsgService {

    private final MongoTemplate mongoTemplate;
    private final MsgConverter msgConverter;
    private final PersonalMsgService personalMsgService;
    private final GroupMsgService groupMsgService;

    public void insert(ManageMsg msg) {
        if (MsgType.PERSONAL.equals(msg.getMsgHeader().getMsgType())) {
            personalMsgService.insert(msg.getMsgBody());
        } else {
            groupMsgService.insert(msg.getMsgBody());
        }
    }

    public void updateStatus(Long msgId, MsgTransferStatus status) {
        Query query = Query.query(Criteria
                .where(MsgEntityFieldName.MSG_ID).is(msgId)
        );
        Update update = new Update().set(MsgEntityFieldName.TRANSFET_STATUS, status);
        mongoTemplate.updateFirst(query, update, MsgEntity.class);
    }
}
