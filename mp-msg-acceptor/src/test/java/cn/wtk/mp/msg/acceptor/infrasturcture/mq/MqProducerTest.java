package cn.wtk.mp.msg.acceptor.infrasturcture.mq;

import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.msg.acceptor.infrasturcture.config.MsgMqTopic;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@Slf4j(topic = "test")
@DisplayName("MqProviderTest测试")
@SpringBootTest
class MqProducerTest {

    @Autowired
    private MqProducer provider;
    @Autowired
    private MsgMqTopic msgMqTopic;

    @Test
    void testSendPersonal() {
        PersonalMsg msg = new PersonalMsg();
        msg.setMsgId(12313L);
        msg.setRcvrId(40L);
        msg.setRcvrId(39L);
        msg.setData("msg data");
        msg.setMsgTopic("/test");
        msg.setRelationTopic("/frends/qq");
        msg.setSendTime(new Date());
        msg.setAppId(1L);
        try {
            provider.send(msgMqTopic.getPersonalMsg(), msg.getRcvrId(), msg);
        } catch (ExecutionException | InterruptedException e) {
            log.warn("{}", e.getMessage());
        }
    }

    @Test
    void testSendGroup() {
    }
}