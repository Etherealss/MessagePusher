package cn.wtk.mp.auth.domain.credential;

import cn.wtk.mp.auth.infrastructure.token.ITokenHandler;
import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.internal.BugException;
import cn.wtk.mp.common.base.utils.UUIDUtil;
import cn.wtk.mp.common.security.exception.TokenException;
import cn.wtk.mp.common.security.service.auth.TokenCredential;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author wtk
 * @date 2023-02-05
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TokenCredentialService {
    private final ITokenHandler tokenHandler;

    /**
     * 为了保存 TokenCredential 子类中的特殊字段，例如 ServerTokenCredential 的 serverId
     * 需要在上层先行创建 TokenCredential 对象，再传到这里
     * @param credential
     * @param tokenCredentialSpec
     */
    public void completeAndSave(TokenCredential credential, TokenCredentialSpec tokenCredentialSpec) {
        UUID token = UUIDUtil.get();
        credential.setToken(token.toString());
        Date tokenExpireAt = new Date(tokenCredentialSpec.getExpireMs() + System.currentTimeMillis());
        credential.setExpireAt(tokenExpireAt);
        credential.setTokenTopic(tokenCredentialSpec.getTokenTopic());
        tokenHandler.saveToken(credential);
    }

    @SuppressWarnings("unchecked")
    public <T extends TokenCredential> T verifyAndGet(String token, Class<T> credentialType) {
        TokenCredential credential = tokenHandler.getToken(token);
        if (credential == null) {
            throw new TokenException(ApiInfo.USER_TOKEN_INVALID);
        }
        if (credential.getClass() == credentialType) {
            return (T) credential;
        } else {
            throw new BugException("Token 类型不匹配，无法强转");
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends TokenCredential> T verifyAndInvalidateToken(String token, Class<T> credentialType) {
        TokenCredential credential = tokenHandler.getAndDeleteToken(token);
        if (credential == null) {
            throw new TokenException(ApiInfo.USER_TOKEN_INVALID);
        }
        credential.setExpireAt(new Date());
        if (credential.getClass() == credentialType) {
            return (T) credential;
        } else {
            throw new BugException("Token 类型不匹配，无法强转");
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends TokenCredential> T invalidateToken(String token, Class<T> credentialType) {
        TokenCredential credential = tokenHandler.deleteToken(token);
        if (credential.getClass() == credentialType) {
            return (T) credential;
        } else {
            throw new BugException("Token 类型不匹配，无法强转");
        }
    }
}
