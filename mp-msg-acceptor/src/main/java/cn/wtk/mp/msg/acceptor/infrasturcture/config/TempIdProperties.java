package cn.wtk.mp.msg.acceptor.infrasturcture.config;

import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgHandlerSpec;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @see MsgHandlerSpec#getTempId()
 * @see MsgHandlerSpec#getPreMsgTempId() ()
 * @author wtk
 * @date 2023-02-12
 */
@Configuration
@ConfigurationProperties("mp.acceptor.temp-id")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
@Slf4j
public class TempIdProperties {
    @NotEmpty
    String cacheKey;
    @NotNull
    Long expireMs;

    @PostConstruct
    public void init() {
        log.info("ResendConfig.cacheKey: {}", cacheKey);
        log.info("ResendConfig.expireMs: {}", expireMs);
    }
}
