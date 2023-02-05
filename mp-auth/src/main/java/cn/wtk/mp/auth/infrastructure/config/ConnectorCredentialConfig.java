package cn.wtk.mp.auth.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 认证服务创建的 userToken 的缓存相关配置
 * @author wtk
 * @date 2022-08-30
 */
@Configuration
@ConfigurationProperties(prefix = "mp.auth.token.connector")
@Validated
@Getter
@Setter
public class ConnectorCredentialConfig {
    @NotNull
    private Long expireMs;

    @NotBlank
    private String tokenTopic;

}
