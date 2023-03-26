package cn.wtk.mp.msg.acceptor.infrasturcture.mq;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.pojo.Result;
import cn.wtk.mp.common.msg.entity.Msg;
import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgHandlerSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
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

    public Result<Long> sendMsg(Msg msg, MsgHandlerSpec spec) {
        try {
            SendResult<Long, Msg> result = this.send(msg.getMsgTopic(), msg.getRcvrId(), msg);
            // 入参是 Nullable，需要判断
            if (log.isInfoEnabled() && result != null) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                log.info(
                        "生产者成功发送消息到topic:{} partition:{}的消息",
                        recordMetadata.topic(),
                        recordMetadata.partition()
                );
            }
            return Result.ok(msg.getMsgId());
        } catch (ExecutionException | InterruptedException e) {
            log.warn("消息发送至 MQ 失败：{}", e.getMessage());
            return new Result<>(
                    false,
                    ApiInfo.MSG_SEND_FAIL,
                    "消息发送至 MQ 失败：" + e.getMessage()
            );
        }
    }
    public SendResult<Long, Msg> send(String topic, Long key, Msg msg) throws ExecutionException, InterruptedException {
        // MQ 在发送失败时会自动重试，重试次数由配置文件指定。此处不必做无意义的重试
        return kafkaTemplate.send(topic, key, msg).get();
    }
}
