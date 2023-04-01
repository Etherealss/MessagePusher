package cn.wtk.mp.msg.acceptor.infrasturcture.mq;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.msg.dto.mq.KafkaMsg;
import cn.wtk.mp.common.msg.dto.mq.ManagerMsgBody;
import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.converter.MsgConverter;
import cn.wtk.mp.msg.acceptor.infrasturcture.config.MsgMqTopicProperties;
import cn.wtk.mp.msg.acceptor.infrasturcture.exception.SendMsgException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Component
@Slf4j
@RequiredArgsConstructor
@Validated
public class MqMsgProducer {

    private final KafkaTemplate<Long, KafkaMsg> kafkaTemplate;
    private final MsgConverter converter;
    private final MsgMqTopicProperties msgMqTopicProperties;

    public void produce(@Valid KafkaMsg kafkaMsg) throws SendMsgException {
        if (log.isTraceEnabled()) {
            log.trace("发布Kafka消息：{}", kafkaMsg);
        }
        try {
            ManagerMsgBody msgBody = kafkaMsg.getMsgBody();
            Long key = msgBody.getMsgType().equals(MsgType.PERSONAL) ?
                    msgBody.getRcvrId() : msgBody.getGroupId();
            SendResult<Long, KafkaMsg> result = this.produce(key, kafkaMsg);
            if (log.isInfoEnabled()) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                log.info(
                        "生产者成功发送消息到topic:{} partition:{}的消息",
                        recordMetadata.topic(),
                        recordMetadata.partition()
                );
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new SendMsgException(ApiInfo.MSG_SEND_FAIL, "消息发送至 MQ 失败：" + e.getMessage(), e);
        }
    }

    private SendResult<Long, KafkaMsg> produce(Long key, KafkaMsg msg) throws ExecutionException, InterruptedException {
        // MQ 在发送失败时会自动重试，重试次数由配置文件指定。此处不必做无意义的重试
        return kafkaTemplate.send(msgMqTopicProperties.getMsgTopic(), key, msg).get();
    }
}
