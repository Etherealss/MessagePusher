package cn.wtk.mp.connect.infrastructure.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023/3/28
 */
@Configuration
@ConfigurationProperties("mp.connector.address.cache")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class ConnectorAddressCacheProperties {
    @NotBlank String routeCacheKey;
    @NotBlank String connectCacheKey;
    @NotNull Long routeExpireMs;
    @NotNull Long connectExpireMs;
}
