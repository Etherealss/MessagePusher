package cn.wtk.mp.common.security.service.auth.user;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.utils.ServletUtil;
import cn.wtk.mp.common.base.utils.SpringUtil;
import cn.wtk.mp.common.security.config.UserCredentialConfig;
import cn.wtk.mp.common.security.exception.TokenException;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 暂存当前线程中的用户权限信息
 * @author wtk
 * @date 2022-08-30
 */
public class UserSecurityContextHolder {
    private static final ThreadLocal<UserCredential> USER_CREDENTIALS = new InheritableThreadLocal<>();
    private static final UserCredentialConfig TOKEN_CONFIG =
            SpringUtil.getBean(UserCredentialConfig.class);

    public static void set(UserCredential userCredential) {
        Objects.requireNonNull(userCredential.getUserId());
        Objects.requireNonNull(userCredential.getUsername());
        Objects.requireNonNull(userCredential.getToken());
        Objects.requireNonNull(userCredential.getRefreshToken());
        Objects.requireNonNull(userCredential.getPermissions());
        Objects.requireNonNull(userCredential.getRoles());
        USER_CREDENTIALS.set(userCredential);
    }

    @Nullable
    public static UserCredential get() {
        return USER_CREDENTIALS.get();
    }

    @NonNull
    public static UserCredential require() {
        UserCredential userCredential = USER_CREDENTIALS.get();
        if (userCredential == null) {
            String token = ServletUtil.getRequest().getHeader(TOKEN_CONFIG.getHeaderName());
            if (!StringUtils.hasText(token)) {
                throw new TokenException(ApiInfo.USER_TOKEN_MISSING);
            } else {
                throw new TokenException(ApiInfo.USER_TOKEN_INVALID);
            }
        }
        return userCredential;
    }

    public static void remove() {
        USER_CREDENTIALS.remove();
    }
}
