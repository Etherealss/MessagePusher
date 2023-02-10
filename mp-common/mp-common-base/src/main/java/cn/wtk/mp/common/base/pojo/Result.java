package cn.wtk.mp.common.base.pojo;


import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author wtk
 * @description 统一的接口信息包，用于前后端交互
 * @date 2021-08-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private boolean success;

    /** 结果码，用于判断响应情况 */
    private int code;

    /** 响应情况说明，如“用户密码错误” */
    private String message;

    /** 响应的数据，可能为空 */
    private T data;

    public static <T> Result<T> ok() {
        return new Result<>(true, ApiInfo.OK);
    }

    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>(true, ApiInfo.OK);
        result.setData(data);
        return result;
    }
    public static <T> Result<T> ok(String message) {
        return new Result<>(true, ApiInfo.OK, message);
    }

    public Result(boolean success, ApiInfo apiInfo) {
        this(success, apiInfo.getCode(), apiInfo.getMessage(), null);
    }


    public Result(boolean success, ApiInfo apiInfo, String description) {
        this(success, apiInfo.getCode(),
                apiInfo.getMessage() + " " + description, null);
    }
    /**
     * 用自定义的异常生成ApiMsg
     * 自定义异常的message包含了ApiInfo的说明和自定义描述
     * @param e
     */
    public Result(BaseException e) {
        this(false,  e.getCode(), e.getMessage(), null);
    }

    /**
     * 使用非自定义的其他异常生成ApiMsg
     * 手动拼接ApiInfo的说明和自定义描述
     * @param throwable
     */
    public Result(Throwable throwable) {
        this.success = false;
        this.code = ApiInfo.SERVER_ERROR.getCode();
        String message = ApiInfo.SERVER_ERROR.getMessage();
        if (throwable.getMessage().length() > 0) {
            message += (": " + throwable.getMessage());
        }
        this.message = message;
    }
}
