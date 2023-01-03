package cn.wtk.mp.uid.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023-01-02
 */
@Configuration
@ConfigurationProperties(prefix = "mp.uid.server")
@Validated
@Getter
@Setter
public class UidServerConfig {
    @NotNull
    private Integer machineId;
    @NotNull
    private Integer dataCenterId;
}
