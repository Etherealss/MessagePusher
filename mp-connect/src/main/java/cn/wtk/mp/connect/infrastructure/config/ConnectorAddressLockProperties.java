package cn.wtk.mp.connect.infrastructure.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023/3/28
 */
@Configuration
@ConfigurationProperties("mp.connector.address.lock")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class ConnectorAddressLockProperties {
    @NotNull
    private Integer totalRetryTimes = 5;
    @NotNull
    private Integer retryIntervalMs = 1000;
    @NotNull
    private Boolean lockFailedThrowException = true;
    @NotEmpty
    private String lockKeyPrefix;
}
