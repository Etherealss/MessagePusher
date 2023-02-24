package cn.wtk.mp.connect.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023-01-07
 */
@Configuration
@ConfigurationProperties("mp.connect.netty")
@Validated
@Getter
@Setter
public class NettyServerConfig {

    @NotBlank
    private String ip;

    @NotNull
    private Integer port;

    @NotBlank
    private String path;

    @NotNull
    private Integer maxFrameSize = 1024;

    /**
     * 心跳检测超时时间，默认两分半钟
     */
    @NotNull
    private Integer idleSeconds = 180;

    /**
     * 服务端 accept 队列大小
     */
    @NotNull
    private Integer serverTcpBlacklog = 1024;

    /**
     * TCP Keepalive 机制，实现 TCP 层级的心跳保活功能
     */
    @NotNull
    private Boolean enableTcpKeepalive = true;

    /**
     * 允许较小的数据包的发送，降低延迟
     */
    @NotNull
    private Boolean enableTcpNodelay = true;
}
