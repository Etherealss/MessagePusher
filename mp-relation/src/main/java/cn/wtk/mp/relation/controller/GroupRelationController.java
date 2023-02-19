package cn.wtk.mp.relation.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.annotation.InternalAuth;
import cn.wtk.mp.relation.application.GroupAppService;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.JoinGroupCommand;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.QuitGroupRelationCommand;
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
@RequestMapping("/groups/{groupId}/relations")
@RequiredArgsConstructor
@ResponseAdvice
public class GroupRelationController {
    private final GroupAppService groupAppService;

    @PostMapping
    @InternalAuth
    public void joinGroup(@RequestBody @Validated JoinGroupCommand command,
                          @PathVariable Long groupId) {
        command.setGroupId(groupId);
        groupAppService.joinGroup(command);
    }

    @DeleteMapping
    @InternalAuth
    public void quitGroup(@RequestBody @Validated QuitGroupRelationCommand command,
                          @PathVariable Long groupId) {
        command.setGroupId(groupId);
        groupAppService.quitGroup(command);
    }


    @GetMapping
    @InternalAuth
    public List<Long> getGroupMembers(@PathVariable Long groupId) {
        return groupAppService.getGroupMembers(groupId);
    }

    @GetMapping("/{memberId}")
    @InternalAuth
    public Boolean checkMember(@PathVariable Long groupId,
                               @PathVariable Long memberId) {
        return groupAppService.checkMember(groupId, memberId);
    }
}
