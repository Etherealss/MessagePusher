package cn.wtk.mp.msg.acceptor.domain.acceptor;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.service.SimpleServiceException;
import cn.wtk.mp.common.base.uid.UidGenerator;
import cn.wtk.mp.common.msg.dto.mq.KafkaMsg;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.converter.MsgConverter;
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
    private final MsgSeqHandler msgSeqHandler;
    private final MqMsgProducer mqMsgProducer;
    private final UidGenerator uidGenerator;
    private final MsgConverter msgConverter;

    public Long sendMsg(MsgBody msgBody, MsgHeader header) {
        // TODO 异步并发操作，线程池限流操作，责任链流水线
        if (msgResendHandler.checkAndSet4Duplicate(header.getTempId())) {
            throw new SimpleServiceException(ApiInfo.MSG_DUPILICATE);
        }
        msgSeqHandler.handlerMsgSeq(header);
        long msgId = uidGenerator.nextId();
        msgBody.setMsgId(msgId);
        mqMsgProducer.produce(new KafkaMsg(
                msgConverter.toMsgHeader(header), msgConverter.toMsgBody(msgBody)
        ));
        return msgId;
    }

}
