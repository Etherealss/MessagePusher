package cn.wtk.mp.common.base.exception.rest;


import cn.wtk.mp.common.base.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-13
 */
public class ParamErrorException extends RestException {
    public ParamErrorException() {
        super(ApiInfo.ERROR_PARAM);
    }

    public ParamErrorException(String message) {
        super(ApiInfo.ERROR_PARAM, message);
    }
}
