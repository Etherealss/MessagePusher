package cn.wtk.mp.msg.acceptor.infrasturcture.mq;

import cn.wtk.mp.common.msg.entity.Msg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MqProducer {

    private final KafkaTemplate<Long, Msg> kafkaTemplate;

    public SendResult<Long, Msg> send(String topic, Long key, Msg msg) throws ExecutionException, InterruptedException {
        // MQ 在发送失败时会自动重试，重试次数由配置文件指定。此处不必做无意义的重试
        return kafkaTemplate.send(topic, key, msg).get();
    }
}
