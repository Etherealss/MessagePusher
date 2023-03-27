package cn.wtk.mp.msg.acceptor.domain.acceptor;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.service.SimpleServiceException;
import cn.wtk.mp.common.base.uid.UidGenerator;
import cn.wtk.mp.msg.acceptor.infrasturcture.config.MsgMqTopic;
import cn.wtk.mp.msg.acceptor.infrasturcture.mq.KafkaMsg;
import cn.wtk.mp.msg.acceptor.infrasturcture.mq.MqMsgProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
    private final MqMsgProducer mqMsgProducer;
    private final MsgMqTopic msgMqTopic;
    private final UidGenerator uidGenerator;

    public Long sendMsg(MsgBody msg, MsgHeader spec) {
        // TODO 异步并发操作，线程池限流操作，责任链流水线
        if (msgResendHandler.checkAndSet4Duplicate(spec.getTempId())) {
            throw new SimpleServiceException(ApiInfo.MSG_DUPILICATE);
        }
        msgSeqHandler.handlerMsgSeq(spec);
        long msgId = uidGenerator.nextId();
        msg.setMsgId(msgId);
        mqMsgProducer.produce(new KafkaMsg(spec, msg));
        return msgId;
    }

}
