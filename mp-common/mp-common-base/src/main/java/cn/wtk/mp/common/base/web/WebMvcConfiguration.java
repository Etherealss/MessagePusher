package cn.wtk.mp.common.base.web;


import cn.wtk.mp.common.base.interceptor.ConfigHandlerInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * SpringMVC自定义配置，添加一些自定义功能
 * @author wtk
 * @date 2022-01-26
 */
@Slf4j(topic = "other")
@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final List<HandlerMethodArgumentResolver> customerArgumentResolvers;
    private final List<ConverterFactory<?, ?>> converterFactories;
    private final List<ConfigHandlerInterceptor> interceptors;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        setHttpMessageConverterToFirst(converters);
    }

    /**
     * 解决 ResponseBodyAdvice 处理String返回值时出现 cannot be cast to java.lang.String 的bug
     * 这个bug的原因是Controller返回String时，会被StringHttpMessageConverter解析为页面跳转的指令
     * 而我们实际返回的是Msg对象，会出现强转错误
     * 此处将MappingJackson2HttpMessageConverter处理器提至最前，先于StringHttpMessageConverter处理
     * @param converters
     */
    private void setHttpMessageConverterToFirst(List<HttpMessageConverter<?>> converters) {
        int i = 0;
        for (; i < converters.size(); i++) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                break;
            }
        }
        HttpMessageConverter<?> httpMessageConverter = converters.remove(i);
        converters.add(0, httpMessageConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        for (ConfigHandlerInterceptor interceptor : interceptors) {
            registry.addInterceptor(interceptor)
                    .addPathPatterns(interceptor.getPathPatterns())
                    .excludePathPatterns(interceptor.getExcludePathPatterns())
                    .order(interceptor.getOrder());
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.addAll(customerArgumentResolvers);
        log.debug("SpringMVC 所有参数解析器：{}", argumentResolvers);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        for (ConverterFactory<?, ?> converterFactory : converterFactories) {
            registry.addConverterFactory(converterFactory);
        }
    }
}
