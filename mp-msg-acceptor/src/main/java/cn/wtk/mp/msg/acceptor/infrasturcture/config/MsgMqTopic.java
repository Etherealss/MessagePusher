package cn.wtk.mp.msg.acceptor.infrasturcture.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @date 2023/2/20
 */
@Configuration
@Getter
public class MsgMqTopic {
    @Value("${mp.acceptor.mq.producer.msg.topic}")
    private String msgTopic;
}
