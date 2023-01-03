package cn.wtk.mp.common.base.exception.service;


import cn.wtk.mp.common.base.enums.ApiInfo;

/**
 * @author wtk
 * @description
 * @date 2021-08-12
 */
public class ExistException extends SimpleServiceException {
    public ExistException(Class<?> clazz, String message) {
        super(ApiInfo.EXIST, message);
    }

    public ExistException(Class<?> clazz) {
        super(ApiInfo.EXIST, "对应的" + clazz.getSimpleName() + "已存在");
    }
}
