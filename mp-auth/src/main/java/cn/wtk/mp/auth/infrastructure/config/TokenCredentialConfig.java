package cn.wtk.mp.auth.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author wtk
 * @date 2023-02-05
 */
@Configuration
@ConfigurationProperties(prefix = "mp.auth.token")
@Validated
@Getter
@Setter
public class TokenCredentialConfig {
    @NotBlank
    private String cacheKey;
}
