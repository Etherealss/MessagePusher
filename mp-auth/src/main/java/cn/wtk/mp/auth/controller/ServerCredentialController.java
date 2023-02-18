package cn.wtk.mp.auth.controller;


import cn.wtk.mp.auth.application.ServerAuthenticationAppService;
import cn.wtk.mp.auth.infrastructure.client.command.RegisterServerCommand;
import cn.wtk.mp.auth.domain.auth.server.info.ServerInfoService;
import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.annotation.InternalAuth;
import cn.wtk.mp.common.security.service.auth.TokenCredential;
import cn.wtk.mp.common.security.service.auth.server.ServerAuthCommand;
import cn.wtk.mp.common.security.service.auth.server.ServerTokenCredential;
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
    private final ServerAuthenticationAppService serverAuthenticationAppService;

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
     * serverId 不在 url 里提供，因为大多数时候 serverId 都可以通过 token 解析获取
     */
    @PostMapping("/credentials")
    public ServerTokenCredential createCredential(@Validated @RequestBody ServerAuthCommand command) {
        return serverAuthenticationAppService.createServerCredential(command);
    }

    @InternalAuth
    @GetMapping("/credentials/{token}")
    public TokenCredential verify(@PathVariable String token) {
        return serverAuthenticationAppService.verifyAndGetToken(token);
    }
}
