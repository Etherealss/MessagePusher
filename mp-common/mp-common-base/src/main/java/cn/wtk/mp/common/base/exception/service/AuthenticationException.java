package cn.wtk.mp.common.base.exception.service;


import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * @author wtk
 * @date 2022-08-30
 */
public class AuthenticationException extends BaseException {
    public AuthenticationException(ApiInfo apiInfo) {
        super(apiInfo);
    }

    public AuthenticationException(ApiInfo apiInfo, String message) {
        super(apiInfo, message);
    }

    public AuthenticationException(ApiInfo apiInfo, String message, Throwable e) {
        super(apiInfo, message, e);
    }

    public AuthenticationException(int code, HttpStatus httpStatus) {
        super(code, httpStatus);
    }
}
