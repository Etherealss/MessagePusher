package cn.wtk.mp.relation.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.relation.application.SubRelationAppService;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.sub.CreateSubRelationCommand;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.sub.RemoveSubRelationCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Slf4j
@RestController
@RequestMapping("connectors/{connectorId}/relations")
@RequiredArgsConstructor
@ResponseAdvice
public class SubRelationController {
    private final SubRelationAppService subRelationAppService;

    @PostMapping
    public void createSubRelation(@PathVariable Long connectorId,
                                  @RequestBody @Validated CreateSubRelationCommand command) {
        command.setConnectorId(connectorId);
        subRelationAppService.createSubRelation(command);
    }

    @DeleteMapping
    public void removeSubRelation(@PathVariable Long connectorId,
                                  @RequestBody @Validated RemoveSubRelationCommand command) {
        command.setConnectorId(connectorId);
        subRelationAppService.removeSubRelation(command);
    }

    @GetMapping("/{subrId}")
    public List<String> getSubRelations(@PathVariable Long connectorId,
                                        @PathVariable Long subrId) {
        return subRelationAppService.getSubRelations(connectorId, subrId);
    }

    @GetMapping("/{subrId}/{relationTopic}")
    public Boolean checkSubRelation(@PathVariable Long connectorId,
                                    @PathVariable Long subrId,
                                    @PathVariable String relationTopic) {
        return subRelationAppService.checkSubRelation(connectorId, subrId, relationTopic);
    }
}
