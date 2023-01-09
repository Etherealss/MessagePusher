package cn.wtk.mp.auth.domain.auth.user.credential;

import cn.wtk.mp.auth.infrastructure.config.DisposableCredentialCacheConfig;
import cn.wtk.mp.auth.infrastructure.token.ITokenHandler;
import cn.wtk.mp.common.base.exception.rest.ParamErrorException;
import cn.wtk.mp.common.security.service.auth.disposal.DisposableCredential;
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
public class DisposableCredentialService {
    private final ITokenHandler tokenHandler;
    private final DisposableCredentialCacheConfig credentialCacheConfig;

    public DisposableCredential create(Long appId, Serializable userId) {
        DisposableCredential disposableCredential = new DisposableCredential();
        disposableCredential.setAppId(appId);
        disposableCredential.setKey(userId);
        tokenHandler.createToken(disposableCredential, credentialCacheConfig);
        return disposableCredential;
    }

    public DisposableCredential verifyAndGet(Long appId, Serializable userId, String token) {
        DisposableCredential disposableCredential = tokenHandler.verifyAndInvalidateToken(
                token,
                DisposableCredential.class,
                credentialCacheConfig
        );
        if (!userId.equals((disposableCredential.getKey()))) {
            throw new ParamErrorException("disposable credential key 不匹配");
        }
        if (!appId.equals((disposableCredential.getAppId()))) {
            throw new ParamErrorException("appId 不匹配");
        }
        return disposableCredential;
    }
}
