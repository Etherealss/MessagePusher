package cn.wtk.mp.common.security.exception;


import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.service.AuthenticationException;

/**
 * @author wtk
 * @date 2022-08-30
 */
public class NotPermissionException extends AuthenticationException {
    public NotPermissionException(ApiInfo apiInfo) {
        super(apiInfo);
    }

    public NotPermissionException(ApiInfo apiInfo, String message) {
        super(apiInfo, message);
    }

    public NotPermissionException(String... permissions) {
        this(ApiInfo.NOT_PERMISSION, "缺少权限：" + String.join(",", permissions));
    }
}
