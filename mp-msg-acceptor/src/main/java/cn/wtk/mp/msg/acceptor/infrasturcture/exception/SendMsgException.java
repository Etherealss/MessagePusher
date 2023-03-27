package cn.wtk.mp.msg.acceptor.infrasturcture.exception;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.BaseException;

/**
 * @author wtk
 * @date 2023/3/27
 */
public class SendMsgException extends BaseException {
    public SendMsgException(ApiInfo apiInfo) {
        super(apiInfo);
    }

    public SendMsgException(ApiInfo apiInfo, String message) {
        super(apiInfo, message);
    }

    public SendMsgException(ApiInfo apiInfo, String message, Throwable e) {
        super(apiInfo, message, e);
    }
}
