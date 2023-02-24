package cn.wtk.mp.msg.manager.infrasturcture.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

/**
 * @author wtk
 * @date 2023/2/24
 */
@Configuration
@ConfigurationProperties("mp.async.feign")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class FeignAsyncProperties {
    Integer corePoolSize = 10;
    Integer maxPoolSize = 20;
}
