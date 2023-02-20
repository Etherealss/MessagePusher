package cn.wtk.mp.msg.acceptor.infrasturcture.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;

/**
 * @author wtk
 * @date 2023-02-12
 */
@Configuration
@ConfigurationProperties("mp.acceptor.resend")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@Slf4j
public class ResendProperties {
//    @NotEmpty
    String cacheKey;
//    @NotNull
    Long expireMs;

    @PostConstruct
    public void init() {
        log.info("ResendConfig.cacheKey: {}", cacheKey);
        log.info("ResendConfig.expireMs: {}", expireMs);
    }
}
