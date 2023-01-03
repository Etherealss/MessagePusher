package cn.wtk.mp.common.base.interceptor.pathvariable;


import cn.wtk.mp.common.base.config.PathVariableVerifyConfiguration;
import cn.wtk.mp.common.base.exception.rest.ParamErrorException;
import cn.wtk.mp.common.base.interceptor.ConfigHandlerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 路径变量参数验证
 * 例如对于 {articleId}，会验证这个id存不存在
 * @see PathVariableVerifyConfiguration 是否注入本拦截器
 * @author wang tengkun
 * @date 2022/2/25
 */
@Slf4j
public class PathVariableVerifyInterceptor implements ConfigHandlerInterceptor {

    private final Map<String, PathVariableValidator> validators;

    @Autowired
    public PathVariableVerifyInterceptor(List<PathVariableValidator> validatorList) {
        Map<String, PathVariableValidator> validators = validatorList.stream()
                .collect(Collectors.toMap((PathVariableValidator::validateTarget), (v -> v)));
        this.validators = Collections.unmodifiableMap(validators);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        if (!method.isAnnotationPresent(PathVariableValidated.class)) {
            // 跳过
            return true;
        }
        // 获取路径参数
        Map<String, String> pathVariables =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVariables == null) {
            return true;
        }
        for (Map.Entry<String, String> entry : pathVariables.entrySet()) {
            PathVariableValidator validator = validators.get(entry.getKey());
            if (validator == null) {
                log.info("请求路径参数 '{}' 缺少校验规则", entry.getKey());
                continue;
            }
            String[] paramNames = validator.extraParamNames();
            // 获取路径变量参数的值
            String[] args4Validate = null;
            String curParamValue = pathVariables.get(entry.getKey());
            if (paramNames != null && paramNames.length > 0) {
                List<String> paramValues = Arrays.stream(paramNames)
                        .map(pathVariables::get)
                        .collect(Collectors.toList());
                paramValues.add(curParamValue);
                args4Validate = paramValues.toArray(new String[0]);
            }
            if (args4Validate == null) {
                args4Validate = new String[]{curParamValue};
            }
            // 在最后加上当前要检验的参数
            boolean res;
            try {
                res = validator.validate(args4Validate);
            } catch (IllegalArgumentException e) {
                throw new ParamErrorException("参数" + entry.getKey() + "类型不正确: " + e.getMessage());
            }
            if (!res) {
                throw new ParamErrorException("找不到 '" + entry.getKey() + "' 为 '" + entry.getValue() + "' 的数据");
            }
        }
        return true;
    }
}
