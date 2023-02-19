package cn.wtk.mp.msg.manager.infrasturcture.remote.mq;

import org.springframework.context.annotation.Bean;

/**
 * @author wtk
 * @date 2023/2/19
 */
public class MqComsumerConfiguration {

    @Bean
    public PersonalMqComsumer personalMqComsumer() {
        return new PersonalMqComsumer();
    }

    @Bean
    public GroupMqComsumer groupMqComsumer() {
        return new GroupMqComsumer();
    }
}
