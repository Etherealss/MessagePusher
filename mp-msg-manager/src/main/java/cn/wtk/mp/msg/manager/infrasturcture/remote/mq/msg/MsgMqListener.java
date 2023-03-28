package cn.wtk.mp.msg.manager.infrasturcture.remote.mq.msg;

import cn.wtk.mp.common.msg.dto.mq.KafkaMsg;
import cn.wtk.mp.msg.manager.domain.msg.ManageMsg;
import cn.wtk.mp.msg.manager.domain.msg.MsgBody;
import cn.wtk.mp.msg.manager.domain.msg.MsgHeader;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.MsgConverter;
import cn.wtk.mp.msg.manager.infrasturcture.client.event.ConsumeNewMsgEvent;
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
    private final MsgConverter converter;

    @KafkaListener(
            topics = {"${mp.manager.mq.consumer.msg.topic}"},
            groupId = "${mp.manager.mq.consumer.msg.group}"
    )
    public void consumeMsg(ConsumerRecord<Long, KafkaMsg> record, final Acknowledgment ack) {
        KafkaMsg value = record.value();
        log.info("消费者消费 Msg -> {}", value.getMsgBody().getMsgId());
        MsgBody msgBody = converter.toMsgBody(value.getMsgBody());
        MsgHeader msgHeader = converter.toMsgHead(value.getMsgHeader());
        eventPublisher.publishEvent(new ConsumeNewMsgEvent(new ManageMsg(msgHeader, msgBody), ack));
    }
}
