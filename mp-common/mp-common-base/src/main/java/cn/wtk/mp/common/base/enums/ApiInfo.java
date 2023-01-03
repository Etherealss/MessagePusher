package cn.wtk.mp.common.base.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author wtk
 * @description
 * @date 2021-08-12
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
public enum ApiInfo {

    OK(HttpStatus.OK, 200, "OK"),

    BAD_REQUEST(400000, "请求报文语法错误[参数校验失败]"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401000, "[未认证身份]"),
    FORBIDDEN_REQUEST(HttpStatus.FORBIDDEN, 403000, "[没有权限]"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404000, "[目标不存在]"),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500000, "[服务运行异常]"),
    SERVER_BUSY(HttpStatus.SERVICE_UNAVAILABLE, 500000, "[服务繁忙]"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 503000, "[服务不可用]"),

    MISSING_PARAM(400001, "[参数缺失]"),
    ERROR_PARAM(400002, "[参数不合法]"),
    EXIST(400003, "[目标已存在]"),
    MISMATCH(400004, "[信息不匹配]"),
    OPERATE_UNSUPPORTED(400005, "[请求不支持]"),
    NOT_AUTHOR(400006, "[不是作者]"),

    AUTHORIZATION_FAILED(HttpStatus.FORBIDDEN,400011, "[认证未通过]"),

    LOGIN_FAIL(400201, "[登录失败]"),
    PASSWORD_ERROR(400202, "[密码错误]"),
    LOGIN_USER_NOT_FOUND(400203, "[登录用户不存在]"),
    USER_LOGGED(400204, "[用户已登录]"),
    LOGIN_TYPE_NOT_SUPPORT(400205, "[不支持的登录方式]"),

    CAPTCHA_MISSING(400301, "[未输入验证码]"),
    CAPTCHA_NOT_MATCH(400302, "[验证码不匹配]"),
    CAPTCHA_INVALID(400303, "[验证码无效或已过期]"),
    CAPTCHA_ERROR(400304, "[验证码异常]"),

    USER_TOKEN_MISSING(400401, "[用户token缺失]"),
    USER_TOKEN_INVALID(400402, "[用户token无效或已过期]"),
    SERVER_TOKEN_MISSING(400401, "[服务token缺失]"),
    SERVER_TOKEN_INVALID(400402, "[服务token无效或已过期]"),
    NOT_PERMISSION(HttpStatus.FORBIDDEN, 1001, "[没有访问权限]"),
    NOT_ROLE(HttpStatus.FORBIDDEN, 1002, "[非可访问角色]"),

    LIKE_DUPLICATE(4000501, "重复点赞或取消");

    ;

    final HttpStatus httpStatus;
    final int code;
    final String message;

    ApiInfo(int code, String message) {
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.code = code;
        this.message = message;
    }

    ApiInfo(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
