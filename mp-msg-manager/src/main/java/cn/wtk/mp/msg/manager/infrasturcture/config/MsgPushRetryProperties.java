package cn.wtk.mp.msg.manager.infrasturcture.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023/2/23
 */
@Configuration
@ConfigurationProperties("mp.manager.push.retry")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class MsgPushRetryProperties {
    @NotNull
    Integer retryTimes;

    @NotNull
    Long retryInterval;
}
