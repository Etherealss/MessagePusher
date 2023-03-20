package cn.wtk.mp.msg.manager.infrasturcture.exception;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.BaseException;

/**
 * @author wtk
 * @date 2023-03-20
 */
public class RelationException extends BaseException {
    public RelationException(String message) {
        super(ApiInfo.MSG_REALTION_MISMATCH, message);
    }

    public RelationException(String message, Throwable e) {
        super(ApiInfo.MSG_REALTION_MISMATCH, message, e);
    }
}
