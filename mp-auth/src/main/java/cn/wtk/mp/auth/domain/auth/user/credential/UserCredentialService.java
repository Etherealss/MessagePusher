package cn.wtk.mp.auth.domain.auth.user.credential;

import cn.wtk.mp.auth.infrastructure.config.UserCredentialCacheConfig;
import cn.wtk.mp.auth.infrastructure.token.ITokenHandler;
import cn.wtk.mp.common.base.exception.rest.ParamErrorException;
import cn.wtk.mp.common.security.service.auth.user.UserCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author wtk
 * @date 2023-01-04
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserCredentialService {
    private final ITokenHandler tokenHandler;
    private final UserCredentialCacheConfig credentialCacheConfig;

    public UserCredential create(Long appId, Serializable userId) {
        UserCredential userCredential = new UserCredential();
        userCredential.setAppId(appId);
        userCredential.setUserId(userId);
        tokenHandler.createToken(userCredential, credentialCacheConfig);
        return userCredential;
    }

    public UserCredential verifyAndGet(Long appId, Serializable userId, String token) {
        UserCredential userCredential = tokenHandler.verifyToken(
                token,
                UserCredential.class,
                credentialCacheConfig
        );
        if (!userId.equals((userCredential.getUserId()))) {
            throw new ParamErrorException("userId 不匹配");
        }
        if (!appId.equals((userCredential.getAppId()))) {
            throw new ParamErrorException("appId 不匹配");
        }
        return userCredential;
    }
}
