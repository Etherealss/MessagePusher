package cn.wtk.mp.relation.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.annotation.InternalAuth;
import cn.wtk.mp.common.security.service.auth.server.ServerSecurityContextHolder;
import cn.wtk.mp.relation.application.GroupAppService;
import cn.wtk.mp.relation.infrasturcture.client.command.relation.group.CreateGroupCommand;
import cn.wtk.mp.relation.infrasturcture.client.dto.GroupDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Slf4j
@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
@ResponseAdvice
public class GroupController {
    private final GroupAppService groupAppService;

    @PostMapping
    @InternalAuth
    public Long createGroup(@RequestBody @Validated CreateGroupCommand command) {
        command.setAppId(ServerSecurityContextHolder.require().getServerId());
        return groupAppService.createGroup(command);
    }

    @GetMapping("/{id}")
    @InternalAuth
    public GroupDTO getGroup(@PathVariable Long id) {
        return groupAppService.getGroup(id);
    }
}
