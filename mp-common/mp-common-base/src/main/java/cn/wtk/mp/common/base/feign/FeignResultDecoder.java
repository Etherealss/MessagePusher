package cn.wtk.mp.common.base.feign;


import cn.wtk.mp.common.base.exception.service.ServiceFiegnException;
import cn.wtk.mp.common.base.pojo.Result;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import cn.wtk.mp.common.base.utils.JsonUtil;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author wtk
 * @date 2022-09-02
 */
@Slf4j
public class FeignResultDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException {
        if (response.body() == null) {
            throw new DecodeException(response.status(), "没有返回有效的数据", response.request());
        }
        //对结果进行转换
        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
        Result<?> result;
        try {
            result = JsonUtil.toObject(bodyStr, Result.class);
        } catch (Exception e) {
            throw new DecodeException(
                    response.status(),
                    "没有返回预期的数据，响应体无法转为 Msg.class。相应数据：" + bodyStr,
                    response.request()
            );
        }
        //如果返回错误，且为内部错误，则直接抛出异常
        if (!result.isSuccess()) {
            throw new ServiceFiegnException(result, response.status(), response.request());
        }
        try {
            return JsonUtil.toGenericObject(JsonUtil.toJsonString(result.getData()), type);
        } catch (Exception e) {
            throw new DecodeException(
                    response.status(),
                    "没有返回预期的数据，响应体无法转为 " + type.getTypeName() + "。相应数据：" + bodyStr,
                    response.request()
            );
        }
    }
}
