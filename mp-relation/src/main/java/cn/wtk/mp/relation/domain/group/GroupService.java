package cn.wtk.mp.relation.domain.group;

import cn.wtk.mp.common.base.uid.UidGenerator;
import cn.wtk.mp.relation.domain.group.relation.GroupRelationEntity;
import cn.wtk.mp.relation.domain.group.relation.GroupRelationRepository;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.CreateGroupCommand;
import cn.wtk.mp.relation.infrasturcture.client.converter.GroupRelationConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService {

    private final GroupRelationRepository groupRelationRepository;
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
}
