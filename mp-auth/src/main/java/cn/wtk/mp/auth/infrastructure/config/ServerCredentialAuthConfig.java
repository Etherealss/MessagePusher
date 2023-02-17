package cn.wtk.mp.auth.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 认证服务创建的 serverToken 的缓存相关配置
 * 原名是 ServerCredentialConfig，
 * 但 common-security 下也有一个 ServerCredentialConfig，
 * 用于每个服务缓存自己的 ServerCredential
 * 所以这里改名了
 * @author wtk
 * @date 2022-08-30
 */
@Configuration
@ConfigurationProperties(prefix = "mp.auth.token.server")
@Validated
@Getter
@Setter
public class ServerCredentialAuthConfig {

    @NotNull
    private Long expireMs;

    @NotBlank
    private String tokenTopic;
}
