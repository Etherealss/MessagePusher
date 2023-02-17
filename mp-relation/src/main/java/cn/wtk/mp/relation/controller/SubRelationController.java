package cn.wtk.mp.relation.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.relation.application.SubRelationAppService;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.sub.CreateSubRelationCommand;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.sub.RemoveSubRelationCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Slf4j
@RestController
@RequestMapping("/relation/sub")
@RequiredArgsConstructor
@ResponseAdvice
public class SubRelationController {
    private final SubRelationAppService subRelationAppService;

    @PostMapping
    public void createSubRelation(CreateSubRelationCommand command) {
        subRelationAppService.createSubRelation(command);
    }

    @DeleteMapping
    public void removeSubRelation(RemoveSubRelationCommand command) {
        subRelationAppService.removeSubRelation(command);
    }

    @GetMapping("/{connectorId}/{subrId}")
    public List<String> getSubRelations(@PathVariable Long connectorId, @PathVariable Long subrId) {
        return subRelationAppService.getSubRelations(connectorId, subrId);
    }

    @GetMapping("/{connectorId}/{subrId}/{relationTopic}")
    public Boolean checkSubRelation(@PathVariable Long connectorId,
                                    @PathVariable Long subrId,
                                    @PathVariable String relationTopic) {
        return subRelationAppService.checkSubRelation(connectorId, subrId, relationTopic);
    }
}
