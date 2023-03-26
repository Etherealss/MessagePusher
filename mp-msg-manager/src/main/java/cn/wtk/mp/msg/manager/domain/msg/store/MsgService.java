package cn.wtk.mp.msg.manager.domain.msg.store;

import cn.wtk.mp.common.msg.enums.MsgTransferStatus;
import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.manager.domain.msg.MsgBody;
import cn.wtk.mp.msg.manager.domain.msg.store.group.GroupMsgService;
import cn.wtk.mp.msg.manager.domain.msg.store.personal.PersonalMsgService;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.MsgConverter;
import cn.wtk.mp.msg.manager.infrasturcture.client.dto.MsgDTO;
import cn.wtk.mp.msg.manager.infrasturcture.constant.MsgEntityFieldName;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public void insert(MsgBody msg) {
        MsgEntity entities = msgConverter.toEntity(msg);
        mongoTemplate.insert(entities);
    }

    public MsgDTO getById(Long msgId) {
        Query query = Query.query(Criteria
                .where(MsgEntityFieldName.MSG_ID).is(msgId)
        );
        MsgEntity entity = mongoTemplate.findOne(query, MsgEntity.class);
        return msgConverter.toDto(entity);
    }

    /**
     * 获取发送者和接收者之间的消息
     * @param senderId
     * @param rcvrId
     * @param cursorMsgId
     * @param size
     * @return
     */
    public List<MsgDTO> page(@Nullable Long senderId,
                             @NonNull Long rcvrId,
                             @NonNull Long cursorMsgId,
                             @NonNull String msgTopic,
                             boolean unread,
                             int size) {
        Criteria criteria = Criteria
                .where(MsgEntityFieldName.MSG_TYPE).is(MsgType.PERSONAL)
                .and(MsgEntityFieldName.SENDER_ID).is(senderId)
                .and(MsgEntityFieldName.RCVR_ID).is(rcvrId)
                .and(MsgEntityFieldName.MSG_TOPIC).is(msgTopic)
                .and(MsgEntityFieldName.MSG_ID).gt(cursorMsgId);
        // TODO 将消息改成int，通过比较大小判断消息状态
        if (unread) {
            criteria.and(MsgEntityFieldName.TRANSFET_STATUS).is(MsgTransferStatus.SENT);
        } else {
            Criteria criteria1 = Criteria.where(MsgEntityFieldName.TRANSFET_STATUS).is(MsgTransferStatus.SENT);
            Criteria criteria2 = Criteria.where(MsgEntityFieldName.TRANSFET_STATUS).is(MsgTransferStatus.REND);
            criteria.orOperator(criteria1, criteria2);
        }
        Query query = Query.query(criteria);
        query.limit(size);
        List<MsgEntity> entities = mongoTemplate.find(query, MsgEntity.class);
        if (unread) {
            Update update = new Update().set(MsgEntityFieldName.TRANSFET_STATUS, MsgTransferStatus.REND);
            mongoTemplate.updateMulti(query, update, MsgEntity.class);
        }
        return msgConverter.toDTOs(entities);
    }

    public boolean deleteById(Long msgId) {
        Query query = Query.query(Criteria
                .where(MsgEntityFieldName.MSG_ID).is(msgId)
        );
        DeleteResult result = mongoTemplate.remove(query, MsgEntity.class);
        return result.getDeletedCount() == 1;
    }

    public void updateStatus(Long msgId, MsgTransferStatus status) {
        Query query = Query.query(Criteria
                .where(MsgEntityFieldName.MSG_ID).is(msgId)
        );
        Update update = new Update().set(MsgEntityFieldName.TRANSFET_STATUS, status);
        mongoTemplate.updateFirst(query, update, MsgEntity.class);
    }
}
