package cn.wtk.mp.auth.controller;


import cn.wtk.mp.auth.application.ConnectorAuthenticationAppService;
import cn.wtk.mp.auth.infrastructure.client.command.CreateConnectCredentialCommand;
import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.annotation.InternalAuth;
import cn.wtk.mp.common.security.service.auth.connector.ConnectorCredential;
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
@RequestMapping("/apps/{appId}/connectors/{connectorId}/credentials")
@RequiredArgsConstructor
@ResponseAdvice
public class UserConnectCredentialController {

    private final ConnectorAuthenticationAppService appService;

    /**
     * 创建 token
     */
    @InternalAuth
    @PostMapping
    public ConnectorCredential createCredential(
            @PathVariable Long appId,
            @PathVariable Long connectorId,
            @RequestBody @Validated CreateConnectCredentialCommand command) {
        return appService.create(appId, connectorId, command);
    }

    @InternalAuth
    @GetMapping("/{token}")
    public ConnectorCredential verify(@PathVariable Long appId,
                                      @PathVariable Long connectorId,
                                      @PathVariable String token,
                                      @RequestParam String connectIp,
                                      @RequestParam Integer connectPort) {
        return appService.verifyAndGet(
                appId, connectorId, token, connectIp, connectPort
        );
    }
}
