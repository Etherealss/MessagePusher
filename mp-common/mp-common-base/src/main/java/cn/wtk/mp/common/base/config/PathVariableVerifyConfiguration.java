package cn.wtk.mp.common.base.config;

import cn.wtk.mp.common.base.interceptor.pathvariable.PathVariableValidator;
import cn.wtk.mp.common.base.interceptor.pathvariable.PathVariableVerifyInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * @author wtk
 * @date 2022-09-03
 */
@Configuration
@ConditionalOnProperty(
        prefix = "mp.common",
        name = "path-variable-verify",
        havingValue = "true"
)
public class PathVariableVerifyConfiguration {

    @Bean
    public PathVariableVerifyInterceptor pathVariableVersifyInterceptor(
            List<PathVariableValidator> validatorList) {
        return new PathVariableVerifyInterceptor(validatorList);
    }

}
