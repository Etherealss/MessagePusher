package cn.wtk.mp.common.base.web;

import cn.wtk.mp.common.base.enums.BaseEnum;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author wtk
 * @date 2022-10-13
 */
@Configuration
public class EnumConverterAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(ConverterFactory.class)
    public ConverterFactory<?, BaseEnum> enumConverter() {
        return new IntegerEnumConverter();
    }
}
