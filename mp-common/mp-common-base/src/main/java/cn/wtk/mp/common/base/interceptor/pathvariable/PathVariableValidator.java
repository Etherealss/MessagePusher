package cn.wtk.mp.common.base.interceptor.pathvariable;

/**
 * @see PathVariableVerifyInterceptor
 * @author wang tengkun
 * @date 2022/2/26
 */
public interface PathVariableValidator {

    /**
     * 进行校验
     * @param args 路径变量的值
     * @return
     */
    boolean validate(String... args);

    /**
     * 检验路径参数时，除了待校验的路径参数外，还需要的参数的名字。
     * 比如检查formName时需要在appId下查，那么extraParamNames就需要提供appId的路径变量名
     * @return
     */
    default String[] extraParamNames() {
        return new String[0];
    }

    /**
     * 要检验的路径参数名
     * @return
     */
    String validateTarget();
}
