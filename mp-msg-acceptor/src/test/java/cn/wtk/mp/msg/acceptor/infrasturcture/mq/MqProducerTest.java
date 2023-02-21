package cn.wtk.mp.msg.acceptor.infrasturcture.mq;

import cn.wtk.mp.common.msg.entity.PersonalMsg;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@Slf4j(topic = "test")
@DisplayName("MqProviderTest测试")
@SpringBootTest
class MqProducerTest {

    @Autowired
    private MqProducer provider;

    @Test
    void testSendPersonal() {
        PersonalMsg msg = new PersonalMsg();
        msg.setMsgId(12313L);
        msg.setRcvrId(40L);
        msg.setRcvrId(39L);
        msg.setData("msg data");
        msg.setMsgTopic("/test");
        msg.setRelationTopic("/frends/qq");
        msg.setResend(false);
        msg.setSendTime(new Date());
        msg.setAppId(1L);
//        provider.sendPersonal(msg);
    }

    @Test
    void testSendGroup() {
    }
}