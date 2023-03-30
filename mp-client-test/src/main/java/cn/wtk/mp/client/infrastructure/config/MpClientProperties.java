package cn.wtk.mp.client.infrastructure.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023/3/29
 */
@Configuration
@ConfigurationProperties("mp.client")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class MpClientProperties {
    @NotNull
    Long connectorId;
}
