package cn.wtk.mp.common.security.exception;


import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.service.AuthenticationException;

/**
 * @author wtk
 * @description token异常
 * @date 2021-10-05
 */
public class TokenException extends AuthenticationException {
    public TokenException(ApiInfo apiInfo) {
        super(apiInfo);
    }

    public TokenException(ApiInfo apiInfo, String message) {
        super(apiInfo, message);
    }
}
