package cn.wtk.mp.relation.application;

import cn.wtk.mp.relation.application.command.CreateSubRelationCommand;
import cn.wtk.mp.relation.application.command.RemoveSubRelationCommand;
import cn.wtk.mp.relation.domain.relation.sub.SubRelationService;
import cn.wtk.mp.relation.domain.relation.sub.SubRelationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        SubRelationVO subRelationVO = new SubRelationVO();
        BeanUtils.copyProperties(command, subRelationVO);
        subRelationService.createSubRelation(subRelationVO);
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeSubRelation(RemoveSubRelationCommand command) {
        SubRelationVO subRelationVO = new SubRelationVO();
        BeanUtils.copyProperties(command, subRelationVO);
        subRelationService.createSubRelation(subRelationVO);
    }
}
