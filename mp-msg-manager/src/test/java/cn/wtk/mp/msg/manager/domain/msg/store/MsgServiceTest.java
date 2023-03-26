package cn.wtk.mp.msg.manager.domain.msg.store;

import cn.wtk.mp.MsgManagerApplication;
import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.manager.domain.msg.ManageMsg;
import cn.wtk.mp.msg.manager.domain.msg.MsgBody;
import cn.wtk.mp.msg.manager.domain.msg.MsgHeader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

@Slf4j(topic = "test")
@DisplayName("MsgServiceTest测试")
@SpringBootTest(classes = {MsgManagerApplication.class})
@Rollback(false)
class MsgServiceTest {

    @Autowired
    private MsgService msgService;

    @Test
    void testInsert() {
        MsgHeader header = new MsgHeader();
        header.setMsgType(MsgType.PERSONAL);
        MsgBody body = new MsgBody();
        body.setMsgType(MsgType.PERSONAL);
        body.setMsgTopic("/test123");
        body.setMsgId(11111L);
        body.setAppId(1321312313L);
        body.setSenderId(1L);
        body.setRevrId(2L);
        body.setDetail("MsgDetail");
        body.setPayload("MsgDetail");
        body.setSendTime(new Date());
        ManageMsg msg = new ManageMsg();
        msg.setMsgHeader(header);
        msg.setMsgBody(body);
        msgService.insert(msg);
    }

    @Test
    void testUpdateStatus() {
    }
}