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
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 4010000, "[未认证身份]"),
    FORBIDDEN_REQUEST(HttpStatus.FORBIDDEN, 4030000, "[没有权限]"),
    AUTHORIZATION_FAILED(HttpStatus.FORBIDDEN,4030001, "[认证未通过]"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 4040000, "[目标不存在]"),

    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000000, "[服务运行异常]"),
    SERVER_BUSY(HttpStatus.SERVICE_UNAVAILABLE, 5000000, "[服务繁忙]"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 5030000, "[服务不可用]"),

    // ------------------- 00 通用------------------------
    MISSING_PARAM(4000001, "[参数缺失]"),
    ERROR_PARAM(4000002, "[参数不合法]"),
    EXIST(4000003, "[目标已存在]"),
    MISMATCH(4000004, "[信息不匹配]"),
    OPERATE_UNSUPPORTED(4000005, "[请求不支持]"),
    NOT_AUTHOR(4000006, "[不是作者]"),


    // ------------------- 02 登录 ------------------------
    LOGIN_FAIL(4000201, "[登录失败]"),
    PASSWORD_ERROR(4000202, "[密码错误]"),
    LOGIN_USER_NOT_FOUND(4000203, "[登录用户不存在]"),
    USER_LOGGED(4000204, "[用户已登录]"),
    LOGIN_TYPE_NOT_SUPPORT(4000205, "[不支持的登录方式]"),

    // ------------------- 03 token ------------------------
    USER_TOKEN_MISSING(4000301, "[用户token缺失]"),
    USER_TOKEN_INVALID(4000302, "[用户token无效或已过期]"),
    SERVER_TOKEN_MISSING(4000303, "[服务token缺失]"),
    SERVER_TOKEN_INVALID(4000304, "[服务token无效或已过期]"),
    NOT_PERMISSION(HttpStatus.FORBIDDEN, 4010301, "[没有访问权限]"),
    NOT_ROLE(HttpStatus.FORBIDDEN, 4010302, "[非可访问角色]"),

    // ------------------- 04 消息发送 ------------------------
    MSG_DUPILICATE(HttpStatus.OK, 2000401, "[消息重复]");
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
