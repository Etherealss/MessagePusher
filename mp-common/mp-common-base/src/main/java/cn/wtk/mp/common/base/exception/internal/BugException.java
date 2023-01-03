package cn.wtk.mp.common.base.exception.internal;


import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.BaseException;

/**
 * 有bug
 * @author wtk
 * @date 2022-04-25
 */
public class BugException extends BaseException {
    public BugException(String message) {
        super(ApiInfo.SERVER_ERROR, message);
    }
}
