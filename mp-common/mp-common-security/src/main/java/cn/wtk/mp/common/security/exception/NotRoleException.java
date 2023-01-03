package cn.wtk.mp.common.security.exception;


import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.service.AuthenticationException;

/**
 * @author wtk
 * @date 2022-08-30
 */
public class NotRoleException extends AuthenticationException {
    public NotRoleException(ApiInfo apiInfo) {
        super(apiInfo);
    }

    public NotRoleException(ApiInfo apiInfo, String message) {
        super(apiInfo, message);
    }

    public NotRoleException(String... permissions) {
        this(ApiInfo.NOT_PERMISSION, "缺少角色身份：" + String.join(",", permissions));
    }
}
