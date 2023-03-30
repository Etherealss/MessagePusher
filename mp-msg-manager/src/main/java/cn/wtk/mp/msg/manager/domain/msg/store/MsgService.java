package cn.wtk.mp.msg.manager.domain.msg.store;

import cn.wtk.mp.common.msg.enums.MsgTransferStatus;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
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

    @Transactional(rollbackFor = Exception.class)
    public void insert(MsgBody msg) {
        msg.setSaveTime(new Date());
        MsgEntity entity = msgConverter.toEntity(msg);
        entity.setTransferStatus(MsgTransferStatus.SENDING);
        mongoTemplate.insert(entity);
    }

    public MsgDTO getById(Long msgId, Boolean unread) {
        Query query = Query.query(Criteria
                .where(MsgEntityFieldName.MSG_ID).is(msgId)
        );
        MsgEntity entity;
        if (unread) {
            entity = mongoTemplate.findAndModify(
                    query,
                    new Update().set(MsgEntityFieldName.TRANSFET_STATUS, MsgTransferStatus.REND),
                    MsgEntity.class
            );
        } else {
            entity = mongoTemplate.findOne(query, MsgEntity.class);
        }
        return msgConverter.toDto(entity);
    }

    /**
     * 获取发送者和接收者之间的消息
     * @param senderId 发送者ID
     * @param rcvrId 接收者或群组ID
     * @param cursorMsgId 前一条消息的ID
     * @param msgTopic
     * @param unread
     * @param size
     * @return
     */
    public List<MsgDTO> page(@Nullable Long senderId,
                             @NonNull Long rcvrId,
                             @NonNull Long cursorMsgId,
                             @Nullable String msgTopic,
                             boolean unread,
                             int size) {
        Criteria criteria = Criteria
                .where(MsgEntityFieldName.RCVR_ID).is(rcvrId)
                .and(MsgEntityFieldName.MSG_ID).gt(cursorMsgId);
        if (senderId != null) {
            criteria.and(MsgEntityFieldName.SENDER_ID).is(senderId);
        }
        if (StringUtils.hasText(msgTopic)) {
            criteria.and(MsgEntityFieldName.MSG_TOPIC).is(msgTopic);
        }
        if (unread) {
            criteria.and(MsgEntityFieldName.TRANSFET_STATUS).gte(MsgTransferStatus.getUnreadStatus());
        } else {
            criteria.and(MsgEntityFieldName.TRANSFET_STATUS).gte(MsgTransferStatus.getReadableStatus());
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

    @Transactional
    public void updateStatus(Long msgId, MsgTransferStatus status) {
        Query query = Query.query(Criteria
                .where(MsgEntityFieldName.MSG_ID).is(msgId)
        );
        Update update = new Update().set(MsgEntityFieldName.TRANSFET_STATUS, status);
        mongoTemplate.updateFirst(query, update, MsgEntity.class);
    }
}
