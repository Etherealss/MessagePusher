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

    // ------------------- 00 通用------------------------
    OK(HttpStatus.OK, 2000000, "OK"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 4040000, "[通用-目标不存在]"),
    MISSING_PARAM(4000001, "[通用-参数缺失]"),
    ERROR_PARAM(4000002, "[通用-参数不合法]"),
    EXIST(4000003, "[通用-目标已存在]"),
    MISMATCH(4000004, "[通用-信息不匹配]"),
    OPERATE_UNSUPPORTED(4000005, "[通用-请求不支持]"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5000006, "[服务运行异常]"),
    SERVER_BUSY(HttpStatus.SERVICE_UNAVAILABLE, 5000007, "[服务繁忙]"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 5030008, "[服务不可用]"),

    // ------------------- 02 登录 ------------------------
    PASSWORD_ERROR(4000201, "[登录-密码错误]"),

    // ------------------- 03 auth ------------------------
    USER_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, 4010301, "[鉴权-用户token缺失]"),
    USER_TOKEN_INVALID(HttpStatus.UNAUTHORIZED,4010302, "[鉴权-用户token无效或已过期]"),
    SERVER_TOKEN_MISSING(HttpStatus.UNAUTHORIZED,4010303, "[鉴权-服务token缺失]"),
    SERVER_TOKEN_INVALID(HttpStatus.UNAUTHORIZED,4010304, "[鉴权-服务token无效或已过期]"),
    NOT_PERMISSION(HttpStatus.FORBIDDEN, 4030305, "[鉴权-没有访问权限]"),
    NOT_ROLE(HttpStatus.FORBIDDEN, 4030306, "[鉴权-非可访问角色]"),

    // ------------------- 04 消息发送 ------------------------
    MSG_DUPILICATE(HttpStatus.OK, 2000401, "[消息发送-消息重复]"),
    MSG_REALTION_MISMATCH(4000402, "[消息发送-关系不匹配]"),
    MSG_SEND_FAIL(5000403, "[消息发送-发送失败]"),

    // ------------------- 05 连接 ------------------------
    CONNECT_UNAUTH(4030501, "[连接-未校验]"),
    CONNECT_AUTH_FAIL(4030502, "[连接-校验失败]"),
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
