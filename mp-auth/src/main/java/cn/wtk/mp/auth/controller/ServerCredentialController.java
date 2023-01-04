package cn.wtk.mp.auth.controller;


import cn.wtk.mp.auth.application.ServerApplicationService;
import cn.wtk.mp.auth.domain.auth.server.credential.ServerCredentialService;
import cn.wtk.mp.auth.domain.auth.server.info.RegisterServerCommand;
import cn.wtk.mp.auth.domain.auth.server.info.ServerInfoService;
import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.annotation.InternalAuth;
import cn.wtk.mp.common.security.service.auth.server.ServerAuthCommand;
import cn.wtk.mp.common.security.service.auth.server.ServerCredential;
import cn.wtk.mp.common.security.service.auth.server.ServerSecurityContextHolder;
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
    @InternalAuth
    @PostMapping("/credentials")
    public ServerCredential createCredential(@Validated @RequestBody ServerAuthCommand command) {
        Long serverId = ServerSecurityContextHolder.require().getServerId();
        return serverApplicationService.createServerCredential(serverId, command);
    }

    @InternalAuth
    @GetMapping("/credentials/{token}")
    public ServerCredential verify(@PathVariable String token) {
        Long serverId = ServerSecurityContextHolder.require().getServerId();
        return serverCredentialService.verifyAndGet(serverId, token);
    }
}
