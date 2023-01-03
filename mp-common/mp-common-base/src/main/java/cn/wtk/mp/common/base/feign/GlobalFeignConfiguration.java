package cn.wtk.mp.common.base.feign;


import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用 @Configuration 注解让 Spring 管理该配置类，可以让所有 FeignClient 都使用该配置
 * @author wtk
 * @date 2022-09-02
 */
@Configuration
public class GlobalFeignConfiguration {

    @Bean
    public Decoder feignDecoder() {
        return new FeignResultDecoder();
    }

    @Bean
    public ErrorDecoder feignErrorDecoder() {
        return new FeignResultErrorDecoder(new ErrorDecoder.Default());
    }
}
