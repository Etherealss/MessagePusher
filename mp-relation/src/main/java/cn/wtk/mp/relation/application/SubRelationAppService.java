package cn.wtk.mp.relation.application;

import cn.wtk.mp.relation.domain.relation.sub.SubRelationService;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.sub.CreateSubRelationCommand;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.sub.RemoveSubRelationCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SubRelationAppService {

    private final SubRelationService subRelationService;

    @Transactional(rollbackFor = Exception.class)
    public void createSubRelation(CreateSubRelationCommand command) {
        subRelationService.upsertSubRelation(command);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeSubRelation(RemoveSubRelationCommand command) {
        subRelationService.removeSubRelation(command);
    }

    public List<String> getSubRelations(Long connectorId, Long subrId) {
        return subRelationService.getSubRelations(connectorId, subrId);
    }

    public boolean checkSubRelation(Long connectorId, Long subrId, String relationTopic) {
        return subRelationService.checkSubRelation(connectorId, subrId, relationTopic);
    }
}
