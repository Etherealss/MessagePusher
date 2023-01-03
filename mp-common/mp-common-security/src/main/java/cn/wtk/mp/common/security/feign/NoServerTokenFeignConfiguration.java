package cn.wtk.mp.common.security.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @author wtk
 * @date 2022-10-20
 */
public class NoServerTokenFeignConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return null;
    }
}
