package cn.wtk.mp.msg.acceptor.domain.acceptor;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.pojo.Result;
import cn.wtk.mp.common.base.uid.UidGenerator;
import cn.wtk.mp.common.msg.entity.Msg;
import cn.wtk.mp.msg.acceptor.infrasturcture.config.MsgMqTopic;
import cn.wtk.mp.msg.acceptor.infrasturcture.mq.MqProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

/**
 * @author wtk
 * @date 2023/2/10
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MsgAcceptor {

    private final MsgResendHandler msgResendHandler;
    private final MsgRelationVerifier msgRelationVerifier;
    private final MsgSeqHandler msgSeqHandler;
    private final MqProducer mqProducer;
    private final MsgMqTopic msgMqTopic;
    private final UidGenerator uidGenerator;

    public Result<Long> sendMsg(Msg msg, MsgHandlerSpec spec) {
        // TODO 异步并发操作，线程池限流操作，责任链流水线
        if (msgResendHandler.isDuplicateMsg(spec.getTempId())) {
            return new Result<>(true, ApiInfo.MSG_DUPILICATE);
        }
        msgSeqHandler.handlerMsgSeq(spec);
        long msgId = uidGenerator.nextId();
        msg.setMsgId(msgId);
        try {
            SendResult<Long, Msg> result = mqProducer.send(msgMqTopic.getMsgTopic(), msg.getRcvrId(), msg);
            // 入参是 Nullable，需要判断
            if (log.isInfoEnabled() && result != null) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                log.info(
                        "生产者成功发送消息到topic:{} partition:{}的消息",
                        recordMetadata.topic(),
                        recordMetadata.partition()
                );
            }
            return Result.ok(msgId);
        } catch (ExecutionException | InterruptedException e) {
            log.warn("消息发送至 MQ 失败：{}", e.getMessage());
            return new Result<>(
                    false,
                    ApiInfo.MSG_SEND_FAIL,
                    "消息发送至 MQ 失败：" + e.getMessage()
            );
        }
    }

}
