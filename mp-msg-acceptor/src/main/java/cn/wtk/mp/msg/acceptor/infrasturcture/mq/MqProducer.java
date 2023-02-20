package cn.wtk.mp.msg.acceptor.infrasturcture.mq;

import cn.wtk.mp.common.msg.entity.AbstractMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MqProducer {

    private final KafkaTemplate<Long, AbstractMsg> kafkaTemplate;

    public void send(String topic, Long key, AbstractMsg msg) {
        ListenableFuture<SendResult<Long, AbstractMsg>> future = kafkaTemplate.send(topic, key, msg);
        future.addCallback(
                result -> log.debug(
                        "生产者成功发送消息到topic:{} partition:{}的消息",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition()
                ),
                ex -> log.warn("生产者发送消失败：{}", ex.getMessage())
        );
    }
}
