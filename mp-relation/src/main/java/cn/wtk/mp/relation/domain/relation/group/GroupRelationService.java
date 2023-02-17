package cn.wtk.mp.relation.domain.relation.group;

import cn.wtk.mp.common.base.exception.service.ExistException;
import cn.wtk.mp.common.base.exception.service.NotFoundException;
import cn.wtk.mp.common.database.uid.UidGenerator;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.CreateGroupCommand;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.JoinGroupCommand;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.QuitGroupRelationCommand;
import cn.wtk.mp.relation.infrasturcture.client.converter.GroupRelationConverter;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GroupRelationService {

    private final GroupRelationRepository groupRelationRepository;
    private final MongoTemplate mongoTemplate;
    private final GroupRelationConverter converter;
    private final UidGenerator uidGenerator;

    @Transactional(rollbackFor = Exception.class)
    public Long createGroup(CreateGroupCommand command) {
        GroupRelationEntity entity = converter.toEntity(command);
        long groupId = uidGenerator.nextId();
        entity.setGroupId(groupId);
        entity.setMemberIds(Collections.emptyList());
        groupRelationRepository.save(entity);
        return groupId;
    }

    @Transactional(rollbackFor = Exception.class)
    public void createGroupRelation(JoinGroupCommand command) {
        Query query = Query.query(Criteria
                .where(GroupRelationEntity.GROUP_ID).is(command.getGroupId())
        );
        Update update = new Update().addToSet(GroupRelationEntity.MEMBER_IDS, command.getJoinerId());
        UpdateResult result = mongoTemplate.upsert(query, update, GroupRelationEntity.class);
        if (result.getModifiedCount() == 0) {
            throw new ExistException("连接者：'" + command.getJoinerId() + "' 已加入群：'" + command.getGroupId() + "'");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeGroupRelation(QuitGroupRelationCommand command) {
        Query query = Query.query(Criteria
                .where(GroupRelationEntity.GROUP_ID).is(command.getGroupId())
        );
        Update update = new Update().pull(GroupRelationEntity.MEMBER_IDS, command.getQuitterId());
        mongoTemplate.upsert(query, update, GroupRelationEntity.class);
    }

    public List<Long> getGroupRelations(Long groupId) {
        Query query = Query.query(Criteria
                .where(GroupRelationEntity.GROUP_ID).is(groupId)
        );
        query.fields().include(GroupRelationEntity.MEMBER_IDS);
        GroupRelationEntity entity = mongoTemplate.findOne(query, GroupRelationEntity.class);
        if (entity == null) {
            throw new NotFoundException(GroupRelationEntity.class, groupId.toString());
        }
        if (CollectionUtils.isEmpty(entity.getMemberIds())) {
            return Collections.emptyList();
        } else {
            return entity.getMemberIds();
        }
    }

    public boolean checkGroupRelation(Long groupId, Long memeberId) {
        Query query = Query.query(Criteria
                .where(GroupRelationEntity.MEMBER_IDS).is(memeberId)
        );
        // 判断是否存在时，只查一个字段，提高性能
        query.fields().include(GroupRelationEntity.GROUP_ID);
        GroupRelationEntity value = mongoTemplate.findOne(query, GroupRelationEntity.class);
        return value != null;
    }
}
