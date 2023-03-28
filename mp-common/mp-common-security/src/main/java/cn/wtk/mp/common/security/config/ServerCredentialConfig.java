package cn.wtk.mp.common.security.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 1. 服务与鉴权相关的配置
 * 2. 各个服务验证 serverToken 时，对结果的缓存的相关配置。缓存时长由 Credential 决定
 * @author wtk
 * @date 2022-10-08
 */
@Configuration
@ConfigurationProperties("mp.common.token.server")
@RefreshScope
@Validated
@Getter
@Setter
@Slf4j
@ToString
public class ServerCredentialConfig {
    /**
     * 获取 server-token 的 headerName
     */
    @NotBlank
    private String headerName;

    /**
     * 当前系统的 serverId
     */
    @NotNull
    private Long serverId;

    /**
     * 当前系统的 serverName
     */
    @NotBlank
    private String serverName;

    /**
     * 当前系统的 secret，在申请 serverToken 时使用
     */
    @NotBlank
    private String secret;

    /**
     * 缓存当前系统的 Credential 的 key
     */
    @NotBlank
    private String cacheKey;

    @PostConstruct
    public void init() {
        log.info("ServerCredentialConfig 配置：{}", this.toString());
    }
}
