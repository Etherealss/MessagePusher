package cn.wtk.mp.auth.infrastructure.token;


import cn.wtk.mp.common.security.service.auth.TokenCredential;

/**
 * @author wtk
 * @date 2022-08-30
 */
public interface ITokenHandler {

    void saveToken(TokenCredential credential);

    // TODO updateUserCredential 在用户权限更新时修改token权限缓存

    TokenCredential getToken(String token);

    TokenCredential getAndDeleteToken(String token);

    /**
     * 使 token 失效
     * @param token
     */
    TokenCredential deleteToken(String token);
}
