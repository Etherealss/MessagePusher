package cn.wtk.mp.common.base.exception.service;


import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * 普通的业务异常
 * @author wtk
 * @date 2022-09-03
 */
public class SimpleServiceException extends BaseException {
    public SimpleServiceException(int code, HttpStatus httpStatus) {
        super(code, httpStatus);
    }

    public SimpleServiceException(ApiInfo apiInfo) {
        super(apiInfo);
    }

    public SimpleServiceException(ApiInfo apiInfo, String message) {
        super(apiInfo, message);
    }

    public SimpleServiceException(ApiInfo apiInfo, String message, Throwable e) {
        super(apiInfo, message, e);
    }
}
