package cn.wtk.mp.common.base.exception.service;


import cn.wtk.mp.common.base.pojo.Result;
import feign.Request;
import feign.codec.DecodeException;
import lombok.Getter;
import lombok.ToString;

/**
 * @author wtk
 * @date 2022-09-02
 */
@Getter
@ToString
public class ServiceFiegnException extends DecodeException {
    private final Result<?> result;

    public ServiceFiegnException(Result<?> result, int status, Request request) {
        super(status, result.getMessage(), request);
        this.result = result;
    }
}
