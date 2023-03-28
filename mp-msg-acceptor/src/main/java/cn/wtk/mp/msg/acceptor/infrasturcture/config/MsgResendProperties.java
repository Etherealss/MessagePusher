package cn.wtk.mp.msg.acceptor.infrasturcture.config;

import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgHeader;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @see MsgHeader#getTempId()
 * @see MsgHeader#getPreMsgTempId() ()
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
public class MsgResendProperties {
    
    @NotBlank
    String cacheKey;

    @NotNull
    Long expireMs;

    @PostConstruct
    public void init() {
        log.info("ResendConfig.cacheKey: {}", cacheKey);
        log.info("ResendConfig.expireMs: {}", expireMs);
    }
}
