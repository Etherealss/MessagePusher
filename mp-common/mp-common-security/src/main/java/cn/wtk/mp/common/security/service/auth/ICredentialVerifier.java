package cn.wtk.mp.common.security.service.auth;

import cn.wtk.mp.common.security.config.ServerCredentialConfiguration;

/**
 * 作用：管理各类 token 的验证操作，根据需要缓存验证结果。
 *
 * 有两种实现类，分别用于本地验证和远程验证
 * 其中，在 auth 服务中，会使用 LocalTokenHandler 调用服务本地的方法验证
 * 而在其他服务中，会使用 RemoteTokenVerifier 远程调用 auth 服务验证
 * @see ServerCredentialConfiguration
 * @author wtk
 * @date 2022-10-08
 */
public interface ICredentialVerifier<T extends Credential> {
    T verify(String token);
}
