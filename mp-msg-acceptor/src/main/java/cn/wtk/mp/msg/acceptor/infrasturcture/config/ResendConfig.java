package cn.wtk.mp.msg.acceptor.infrasturcture.config;

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
 * @date 2023-02-12
 */
@Configuration
@ConfigurationProperties("mp.acceptor.resend")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class ResendConfig {
    @NotEmpty
    String cacheKey;
    @NotNull
    Long expireMs;
}
