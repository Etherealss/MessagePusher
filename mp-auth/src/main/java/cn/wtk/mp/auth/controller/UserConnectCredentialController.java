package cn.wtk.mp.auth.controller;


import cn.wtk.mp.auth.application.ConnectorCredentialAppService;
import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.annotation.InternalAuth;
import cn.wtk.mp.common.security.service.auth.connector.ConnectorCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final ConnectorCredentialAppService connectorCredentialAppService;

    /**
     * 创建 token
     */
    @InternalAuth
    @PostMapping
    public ConnectorCredential createCredential(@PathVariable Long appId,
                                                @PathVariable Long connectorId) {
        return connectorCredentialAppService.create(appId, connectorId);
    }

    @InternalAuth
    @GetMapping("/{token}")
    public ConnectorCredential verify(@PathVariable Long appId,
                                      @PathVariable Long connectorId,
                                      @PathVariable String token) {
        return connectorCredentialAppService.verifyAndGet(appId, connectorId, token);
    }
}
