package cn.wtk.mp.common.base.exception.rest;


import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * @author wtk
 * @date 2022-09-03
 */
public class RestException extends BaseException {

    public RestException(int code, HttpStatus httpStatus) {
        super(code, httpStatus);
    }

    public RestException(ApiInfo apiInfo) {
        super(apiInfo);
    }

    public RestException(ApiInfo apiInfo, String message) {
        super(apiInfo, message);
    }

    public RestException(ApiInfo apiInfo, String message, Throwable e) {
        super(apiInfo, message, e);
    }
}
