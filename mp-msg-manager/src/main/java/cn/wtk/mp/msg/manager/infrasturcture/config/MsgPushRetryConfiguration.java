package cn.wtk.mp.msg.manager.infrasturcture.config;

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
public class MsgPushRetryConfiguration {

    public static final String BEAN_NAME = "msgPushRetryTemplate";

    @Bean(value = BEAN_NAME)
    public RetryTemplate msgPushRetryTemplate(MsgPushRetryProperties pushRetryProperties) {
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(pushRetryProperties.getRetryTimes());
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(pushRetryProperties.getRetryInterval());
        return RetryTemplate.builder()
                .customPolicy(retryPolicy)
                .customBackoff(backOffPolicy)
                .build();
    }
}
