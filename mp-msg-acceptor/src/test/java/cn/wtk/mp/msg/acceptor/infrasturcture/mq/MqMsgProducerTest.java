package cn.wtk.mp.msg.acceptor.infrasturcture.mq;

import cn.wtk.mp.common.base.uid.UidGenerator;
import cn.wtk.mp.common.msg.dto.mq.KafkaMsg;
import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgBody;
import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgHeader;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.converter.MsgConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.util.Date;

@Slf4j(topic = "test")
@DisplayName("MqProviderTest测试")
@SpringBootTest
class MqMsgProducerTest {

    @Autowired
    private MqMsgProducer provider;
    @Autowired
    private MsgConverter converter;
    @Autowired
    private UidGenerator uidGenerator;

    @Test
    void testSendPersonal() {
        MsgBody msgBody = MsgBody.builder()
                .msgId(uidGenerator.nextId())
                .rcvrId(40L)
                .senderId(39L)
                .payload("payload")
                .detail("detail")
                .msgTopic("/test")
                .msgType(MsgType.PERSONAL)
                .appId(12313131L)
                .sendTime(new Date())
                .build();
        MsgHeader header = MsgHeader.builder()
                .rcvrId(40L)
                .senderId(39L)
                .msgType(MsgType.PERSONAL)
                .needPersistent(false)
                .needRelationVerify(true)
                .relationTopic("/friends/qq")
                .build();
        provider.produce(new KafkaMsg(converter.toMsgHeader(header), converter.toMsgBody(msgBody)));
    }

    @Test
    void testValid() {
        MsgBody msgBody = MsgBody.builder()
                .msgId(uidGenerator.nextId())
                .rcvrId(40L)
                .senderId(39L)
                .payload("payload")
                .detail("detail")
                .msgTopic("/test")
                .msgType(MsgType.PERSONAL)
//                .appId(12313131L)
                .sendTime(new Date())
                .build();
        MsgHeader header = MsgHeader.builder()
                .rcvrId(40L)
                .msgType(MsgType.PERSONAL)
                .relationTopic("/friends/qq")
                .build();
        Assertions.assertThrows(ConstraintViolationException.class,
                () -> provider.produce(
                        new KafkaMsg(converter.toMsgHeader(header), converter.toMsgBody(msgBody))
                )
        );
    }

    @Test
    void testSendGroup() {
    }
}