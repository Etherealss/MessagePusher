package cn.wtk.mp.msg.manager.infrasturcture.remote.mq;

import cn.wtk.mp.common.msg.entity.GroupMsg;
import cn.wtk.mp.common.msg.entity.PersonalMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023/2/20
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MqListener {

    @KafkaListener(
            topics = {"${mp.mq.producer.msg.personal.topic}"},
            groupId = "${mp.mq.producer.msg.personal.group}"
    )
    public void consumePersonalMsg(ConsumerRecord<String, PersonalMsg> record) {
        log.info("消费者消费PersonalMsg -> {}", record.value());
    }

    @KafkaListener(
            topics = {"${mp.mq.producer.msg.group.topic}"},
            groupId = "${mp.mq.producer.msg.group.group}"
    )
    public void consumeGroupMsg(ConsumerRecord<String, GroupMsg> record) {
        log.info("消费者消费GroupMsg -> {}", record.value());
    }
}
