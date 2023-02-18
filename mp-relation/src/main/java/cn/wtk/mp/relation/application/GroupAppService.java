package cn.wtk.mp.relation.application;

import cn.wtk.mp.relation.domain.group.relation.GroupRelationService;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.CreateGroupCommand;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.JoinGroupCommand;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.QuitGroupRelationCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GroupAppService {
    private final GroupRelationService groupRelationService;
    private final GroupAppService groupAppService;

    public Long createGroup(CreateGroupCommand command) {
        return groupAppService.createGroup(command);
    }

    public void joinGroup(JoinGroupCommand command) {
        groupRelationService.createGroupRelation(command);
    }

    public void quitGroup(QuitGroupRelationCommand command) {
        groupRelationService.removeGroupRelation(command);
    }

    public List<Long> getGroupRelations(Long groupId) {
        return groupRelationService.getGroupRelations(groupId);
    }

    public boolean checkGroupRelation(Long groupId, Long memberId) {
        return groupRelationService.checkGroupRelation(groupId, memberId);
    }
}
