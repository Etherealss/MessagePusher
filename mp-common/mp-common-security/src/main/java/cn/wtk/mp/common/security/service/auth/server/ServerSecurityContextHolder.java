package cn.wtk.mp.common.security.service.auth.server;


import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.utils.ServletUtil;
import cn.wtk.mp.common.base.utils.SpringUtil;
import cn.wtk.mp.common.security.config.ServerCredentialConfig;
import cn.wtk.mp.common.security.exception.TokenException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 服务间鉴权。保存当前请求的服务的 ServerCredential
 * @author wtk
 * @date 2022-08-30
 */
public class ServerSecurityContextHolder {
    private static final ThreadLocal<ServerTokenCredential> CREDENTIALS = new InheritableThreadLocal<>();
    private static final ServerCredentialConfig TOKEN_CONFIG = SpringUtil.getBean(ServerCredentialConfig.class);

    public static void set(ServerTokenCredential credential) {
        Objects.requireNonNull(credential.getServerId());
        Objects.requireNonNull(credential.getServerName());
        Objects.requireNonNull(credential.getToken());
        CREDENTIALS.set(credential);
    }

    @Nullable
    public static ServerTokenCredential get() {
        return CREDENTIALS.get();
    }

    @NonNull
    public static ServerTokenCredential require() {
        ServerTokenCredential credential = CREDENTIALS.get();
        if (credential == null) {
            String token = ServletUtil.getRequest().getHeader(TOKEN_CONFIG.getHeaderName());
            if (!StringUtils.hasText(token)) {
                throw new TokenException(ApiInfo.SERVER_TOKEN_MISSING);
            } else {
                throw new TokenException(ApiInfo.SERVER_TOKEN_INVALID);
            }
        }
        return credential;
    }

    public static void remove() {
        CREDENTIALS.remove();
    }
}
