package cn.wtk.mp.auth.controller;


import cn.wtk.mp.auth.domain.auth.user.credential.UserCredentialService;
import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.annotation.InternalAuth;
import cn.wtk.mp.common.security.service.auth.server.ServerSecurityContextHolder;
import cn.wtk.mp.common.security.service.auth.user.UserCredential;
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
@RequestMapping("/apps/users/{userId}/credentials")
@RequiredArgsConstructor
@ResponseAdvice
public class UserCredentialController {

    private final UserCredentialService userCredentialService;

    /**
     * 创建 token
     */
    @InternalAuth
    @PostMapping
    public UserCredential createCredential(@PathVariable Serializable userId) {
        Long appId = ServerSecurityContextHolder.require().getServerId();
        return userCredentialService.create(appId, userId);
    }

    @InternalAuth
    @GetMapping("/{token}")
    public UserCredential verify(@PathVariable Serializable userId,
                                 @PathVariable String token) {
        Long appId = ServerSecurityContextHolder.require().getServerId();
        return userCredentialService.verifyAndGet(appId, userId, token);
    }
}
