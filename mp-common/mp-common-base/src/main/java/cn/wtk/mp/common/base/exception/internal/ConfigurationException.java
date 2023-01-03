package cn.wtk.mp.common.base.exception.internal;


import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.BaseException;

/**
 * @author wtk
 * @date 2022-09-06
 */
public class ConfigurationException extends BaseException {
    public ConfigurationException(String message) {
        super(ApiInfo.SERVER_ERROR, message);
    }
}