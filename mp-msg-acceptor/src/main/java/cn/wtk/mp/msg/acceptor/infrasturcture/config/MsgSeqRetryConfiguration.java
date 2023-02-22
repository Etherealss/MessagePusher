package cn.wtk.mp.msg.acceptor.infrasturcture.config;

import cn.wtk.mp.msg.acceptor.infrasturcture.exception.MsgSeqRetryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * @author wtk
 * @date 2023/2/22
 */
@Configuration
@Slf4j
public class MsgSeqRetryConfiguration {

    public static final String BEAN_NAME = "msgSeqRetryTemplate";

    @Bean(value = BEAN_NAME)
    public RetryTemplate msgSeqRetryTemplate(MsgSeqProperties msgSeqProperties) {
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(msgSeqProperties.getRetryTimes());
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(msgSeqProperties.getRetryInterval());
        return RetryTemplate.builder()
                .customPolicy(retryPolicy)
                .customBackoff(backOffPolicy)
                .build();
    }
}
