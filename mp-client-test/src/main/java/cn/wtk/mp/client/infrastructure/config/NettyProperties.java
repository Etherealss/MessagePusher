package cn.wtk.mp.client.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023/3/27
 */
@Configuration
@ConfigurationProperties("mp.client.netty")
@Validated
@Getter
@Setter
public class NettyProperties {

    @NotBlank
    private String serverIp;

    @NotNull
    private Integer serverPort;

    @NotNull
    private Integer maxFrameSize = 1024 * 1024;
}
