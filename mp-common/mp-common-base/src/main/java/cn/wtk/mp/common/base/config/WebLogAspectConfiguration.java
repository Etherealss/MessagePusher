package cn.wtk.mp.common.base.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cn.wtk.mp.common.base.web.WebLogAspect;

/**
 * @author wtk
 * @date 2022-10-20
 */
@Configuration
@ConditionalOnProperty(prefix = "mp.common", name = "web-log", havingValue = "true")
public class WebLogAspectConfiguration {
    @Bean
    public WebLogAspect webLogAspect() {
        return new WebLogAspect();
    }
}
