package cn.wtk.mp.msg.manager.domain.msg.store.personal;

import cn.wtk.mp.common.msg.enums.MsgTransferStatus;
import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.manager.domain.msg.MsgBody;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.PersonalMsgConverter;
import cn.wtk.mp.msg.manager.infrasturcture.client.dto.PersonalMsgDTO;
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
public class PersonalMsgService {

    private final MongoTemplate mongoTemplate;
    private final PersonalMsgConverter msgConverter;

    public void insert(MsgBody msg) {
        PersonalMsgEntity entities = msgConverter.toEntity(msg);
        mongoTemplate.insert(entities);
    }

    public PersonalMsgDTO getById(Long msgId) {
        Query query = Query.query(Criteria
                .where(MsgEntityFieldName.MSG_ID).is(msgId)
        );
        PersonalMsgEntity entity = mongoTemplate.findOne(query, PersonalMsgEntity.class);
        return msgConverter.toDTO(entity);
    }

    /**
     * 获取发送者和接收者之间的消息
     * @param senderId
     * @param rcvrId
     * @param cursorMsgId
     * @param size
     * @return
     */
    public List<PersonalMsgDTO> page(@Nullable Long senderId,
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
        List<PersonalMsgEntity> entities;
        entities = mongoTemplate.find(query, PersonalMsgEntity.class);
        if (unread) {
            Update update = new Update().set(MsgEntityFieldName.TRANSFET_STATUS, MsgTransferStatus.REND);
            mongoTemplate.updateMulti(query, update, PersonalMsgEntity.class);
        }
        return msgConverter.toDTO(entities);
    }

    public boolean deleteById(Long msgId) {
        Query query = Query.query(Criteria
                .where(MsgEntityFieldName.MSG_ID).is(msgId)
        );
        DeleteResult result = mongoTemplate.remove(query, PersonalMsgEntity.class);
        return result.getDeletedCount() == 1;
    }
}
