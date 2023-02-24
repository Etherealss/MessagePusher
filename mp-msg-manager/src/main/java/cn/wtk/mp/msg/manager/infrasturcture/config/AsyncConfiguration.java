package cn.wtk.mp.msg.manager.infrasturcture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author wtk
 * @date 2023/2/24
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {

    public static final String FEIGN = "feignTaskExecutor";

    @Bean(name = FEIGN)
    public ThreadPoolTaskExecutor feignTaskExecutor(FeignAsyncProperties feignAsyncProperties) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(50);
        return taskExecutor;
    }
}
