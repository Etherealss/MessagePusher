package cn.wtk.mp.msg.acceptor.infrasturcture.mq;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author wtk
 * @date 2023/2/20
 */
@Slf4j(topic = "test")
@DisplayName("MqProviderTest测试")
@SpringBootTest
public class KafkaTemplateTest {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void sendMsg() {
        kafkaTemplate.send("msg-personal", "12312313");
    }
}
