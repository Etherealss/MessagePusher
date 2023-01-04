package cn.wtk.mp.auth.controller;


import cn.wtk.mp.auth.application.ServerApplicationService;
import cn.wtk.mp.auth.domain.auth.server.credential.ServerCredentialService;
import cn.wtk.mp.auth.domain.auth.server.info.RegisterServerCommand;
import cn.wtk.mp.auth.domain.auth.server.info.ServerInfoService;
import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.service.auth.server.ServerAuthCommand;
import cn.wtk.mp.common.security.service.auth.server.ServerCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Slf4j
@RestController
@RequestMapping("/servers")
@RequiredArgsConstructor
@ResponseAdvice
public class ServerCredentialController {
    private final ServerInfoService serverInfoService;
    private final ServerCredentialService serverCredentialService;
    private final ServerApplicationService serverApplicationService;

    /**
     * 注册服务
     * @param command
     * @return
     */
    @PostMapping
    public Long register(@Validated @RequestBody RegisterServerCommand command) {
        return serverInfoService.createAuthInfo(command);
    }

    /**
     * 创建 token
     */
    @PostMapping("/{serverId}/credentials")
    public ServerCredential createCredential(@PathVariable Long serverId,
                                             @Validated @RequestBody ServerAuthCommand command) {
        return serverApplicationService.createServerCredential(serverId, command);
    }

    @GetMapping("/{serverId}/credentials/{token}")
    public ServerCredential verify(@PathVariable Long serverId,
                                   @PathVariable String token) {
        return serverCredentialService.verifyAndGet(serverId, token);
    }
}
