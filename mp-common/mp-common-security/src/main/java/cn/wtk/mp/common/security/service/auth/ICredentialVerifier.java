package cn.wtk.mp.common.security.service.auth;

import cn.wtk.mp.common.security.config.ServerCredentialConfiguration;

/**
 * 作用：管理各类 token 的验证操作，根据需要缓存验证结果。
 * token类型由 credentialType 决定
 *
 * 有两个实现类，分别是 RemoteTokenVerifier 以及 LocalTokenHandler
 * 其中，在 nowander-auth 服务中，会使用 LocalTokenHandler 调用服务本地的方法
 * 而在其他服务中，会使用 RemoteTokenVerifier 远程调用 nowander-auth 服务
 * @see ServerCredentialConfiguration
 * @author wtk
 * @date 2022-10-08
 */
public interface ICredentialVerifier {
    <T extends Credential> T verify(String token, Class<T> credentialType);
}
