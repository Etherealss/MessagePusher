package cn.wtk.mp.common.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * 1. 与用户鉴权相关的配置
 * 2. 各个服务验证 userToken 时，对结果的缓存的相关配置。缓存时长由 Credential 决定
 * @author wtk
 * @date 2022-10-08
 */
@Configuration
@ConfigurationProperties("mp.common.token.user")
@RefreshScope
@Validated
@Getter
@Setter
public class UserCredentialConfig {
    @NotEmpty
    private String headerName;

    @NotEmpty
    private String cacheKey;
}
