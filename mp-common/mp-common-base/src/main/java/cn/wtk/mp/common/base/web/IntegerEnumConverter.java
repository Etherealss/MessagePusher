package cn.wtk.mp.common.base.web;

import cn.wtk.mp.common.base.enums.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * 将Controller中的将参数转为对应的BaseEnum
 * @author wtk
 * @date 2022-04-24
 */
public class IntegerEnumConverter implements ConverterFactory<String, BaseEnum> {
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new IntegerToEnumConverter<>(targetType);
    }

    public static class IntegerToEnumConverter<T extends BaseEnum> implements Converter<String, T> {
        private final Class<T> targetType;

        public IntegerToEnumConverter(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            return BaseEnum.fromCode(targetType, Integer.parseInt(source));
        }
    }
}
