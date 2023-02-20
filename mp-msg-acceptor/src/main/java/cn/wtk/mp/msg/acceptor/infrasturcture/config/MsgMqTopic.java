package cn.wtk.mp.msg.acceptor.infrasturcture.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wtk
 * @date 2023/2/20
 */
@Configuration
@Setter
public class MsgMqTopic {
    @Value("${mp.mq.producer.msg.personal.topic}")
    private String personalMsgTopic;

    @Value("${mp.mq.producer.msg.group.topic}")
    private String groupMsgTopic;
}
