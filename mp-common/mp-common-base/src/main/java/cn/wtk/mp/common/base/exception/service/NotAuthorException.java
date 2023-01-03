package cn.wtk.mp.common.base.exception.service;


import cn.wtk.mp.common.base.enums.ApiInfo;

/**
 * @author wtk
 * @date 2022-02-04
 */
public class NotAuthorException extends SimpleServiceException {
    public NotAuthorException() {
        super(ApiInfo.NOT_AUTHOR);
    }

    public NotAuthorException(String message) {
        super(ApiInfo.NOT_AUTHOR, message);
    }
}
