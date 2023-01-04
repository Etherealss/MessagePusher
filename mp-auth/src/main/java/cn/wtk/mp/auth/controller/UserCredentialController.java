package cn.wtk.mp.auth.controller;


import cn.wtk.mp.auth.domain.auth.user.credential.UserCredentialService;
import cn.wtk.mp.common.base.web.ResponseAdvice;
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
@RequestMapping("/apps/{appId}/users/{userId}/credentials")
@RequiredArgsConstructor
@ResponseAdvice
public class UserCredentialController {

    private final UserCredentialService userCredentialService;

    /**
     * 创建 token
     */
    @PostMapping
    public UserCredential createCredential(@PathVariable Long appId,
                                             @PathVariable Serializable userId) {
        return userCredentialService.create(appId, userId);
    }

    @GetMapping("/{token}")
    public UserCredential verify(@PathVariable Long appId,
                                 @PathVariable Serializable userId,
                                 @PathVariable String token) {
        return userCredentialService.verifyAndGet(appId, userId, token);
    }
}
