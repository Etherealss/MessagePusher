package cn.wtk.mp.auth.controller;


import cn.wtk.mp.auth.domain.auth.user.credential.DisposableCredentialService;
import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.annotation.InternalAuth;
import cn.wtk.mp.common.security.service.auth.server.ServerSecurityContextHolder;
import cn.wtk.mp.common.security.service.auth.disposal.DisposableCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * @author wtk
 * @date 2022-08-30
 */
@Slf4j
@RestController
@RequestMapping("/apps/consumables/{key}/credentials")
@RequiredArgsConstructor
@ResponseAdvice
public class DisposableCredentialController {

    private final DisposableCredentialService disposableCredentialService;

    /**
     * 创建 token
     */
    @InternalAuth
    @PostMapping
    public DisposableCredential createCredential(@PathVariable Serializable key) {
        Long appId = ServerSecurityContextHolder.require().getServerId();
        return disposableCredentialService.create(appId, key);
    }

    @InternalAuth
    @GetMapping("/{token}")
    public DisposableCredential verify(@PathVariable Serializable key,
                                       @PathVariable String token) {
        Long appId = ServerSecurityContextHolder.require().getServerId();
        return disposableCredentialService.verifyAndGet(appId, key, token);
    }
}
