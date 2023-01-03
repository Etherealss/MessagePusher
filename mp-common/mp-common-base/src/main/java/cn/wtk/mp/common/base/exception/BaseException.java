package cn.wtk.mp.common.base.exception;


import cn.wtk.mp.common.base.enums.ApiInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * @author wtk
 * @description 自定义 服务异常
 * @date 2021-08-12
 */
@Setter
@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private int code;
    private HttpStatus httpStatus;

    public BaseException(ApiInfo apiInfo) {
        super(apiInfo.getMessage());
        this.code = apiInfo.getCode();
        this.httpStatus = apiInfo.getHttpStatus();
    }

    public BaseException(ApiInfo apiInfo, String message) {
        super(apiInfo.getMessage() + message);
        this.code = apiInfo.getCode();
        this.httpStatus = apiInfo.getHttpStatus();
    }

    /**
     * @param apiInfo
     * @param message
     * @param e 原始异常
     */
    public BaseException(ApiInfo apiInfo, String message, Throwable e) {
        super(apiInfo.getMessage() + message, e);
        this.code = apiInfo.getCode();
        this.httpStatus = apiInfo.getHttpStatus();
    }
}
