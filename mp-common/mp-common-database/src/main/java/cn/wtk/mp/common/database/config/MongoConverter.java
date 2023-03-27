package cn.wtk.mp.common.database.config;

import org.springframework.core.convert.converter.Converter;

/**
 * @author wtk
 * @date 2023/3/27
 */
public interface MongoConverter<S, T> extends Converter<S, T> {
}
