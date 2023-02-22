package cn.wtk.mp.msg.acceptor.infrasturcture.config;

import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgHandlerSpec;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023/2/22
 */
@Configuration
@ConfigurationProperties("mp.acceptor.seq")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Validated
public class MsgSeqProperties {

    /**
     * 检查消息顺序的时间限制
     * @see MsgHandlerSpec#getPreMsgSendTime()
     */
    @NotNull
    Long timeLimit;

    @NotNull
    Integer retryTimes;

    @NotNull
    Long retryInterval;

    @NotEmpty
    String cacheKey;

    @NotNull
    Long expireMs;
}
