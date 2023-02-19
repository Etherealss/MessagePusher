package cn.wtk.mp.relation.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.annotation.InternalAuth;
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
@RequestMapping("/connectors/{connectorId}/relations")
@RequiredArgsConstructor
@ResponseAdvice
public class SubRelationController {
    private final SubRelationAppService subRelationAppService;

    @PostMapping
    @InternalAuth
    public void createSubRelation(@PathVariable Long connectorId,
                                  @RequestBody @Validated CreateSubRelationCommand command) {
        command.setConnectorId(connectorId);
        subRelationAppService.createSubRelation(command);
    }

    @DeleteMapping
    @InternalAuth
    public void removeSubRelation(@PathVariable Long connectorId,
                                  @RequestBody @Validated RemoveSubRelationCommand command) {
        command.setConnectorId(connectorId);
        subRelationAppService.removeSubRelation(command);
    }

    @GetMapping("/{subrId}")
    @InternalAuth
    public List<String> getSubRelations(@PathVariable Long connectorId,
                                        @PathVariable Long subrId) {
        return subRelationAppService.getSubRelations(connectorId, subrId);
    }

    /**
     * relationTopic 中可能包含与 url 冲突的字符，所以不适用 PathVariable
     * @param connectorId
     * @param subrId
     * @param relationTopic
     * @return
     */
    @GetMapping("/{subrId}/topics")
    @InternalAuth
    public Boolean checkSubRelation(@PathVariable Long connectorId,
                                    @PathVariable Long subrId,
                                    @RequestParam("topic") String relationTopic) {
        return subRelationAppService.checkSubRelation(connectorId, subrId, relationTopic);
    }
}
