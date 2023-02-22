package cn.wtk.mp.msg.manager.infrasturcture.remote.mq.msg;

import cn.wtk.mp.common.msg.entity.Msg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023/2/20
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MsgMqListener {

    private final ApplicationEventPublisher eventPublisher;

    @KafkaListener(
            topics = {"${mp.manager.mq.consumer.msg.topic}"},
            groupId = "${mp.manager.mq.consumer.msg.group}"
    )
    public void consumeMsg(ConsumerRecord<String, Msg> record, final Acknowledgment ack) {
        log.info("消费者消费 Msg -> {}", record.value());
//        eventPublisher.publishEvent(new ConsumeNewMsgEvent(record.value(), ack));
    }
}
