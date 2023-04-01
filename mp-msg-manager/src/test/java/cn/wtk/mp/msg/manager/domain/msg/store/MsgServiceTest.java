package cn.wtk.mp.msg.manager.domain.msg.store;

import cn.wtk.mp.MsgManagerApplication;
import cn.wtk.mp.common.base.uid.UidGenerator;
import cn.wtk.mp.common.msg.enums.MsgTransferStatus;
import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.manager.domain.msg.MsgBody;
import cn.wtk.mp.msg.manager.infrasturcture.client.dto.MsgDTO;
import cn.wtk.mp.msg.manager.infrasturcture.constant.MsgEntityFieldName;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;

@Slf4j(topic = "test")
@DisplayName("MsgServiceTest测试")
@SpringBootTest(classes = {MsgManagerApplication.class})
@Rollback(false)
class MsgServiceTest {

    @Autowired
    private MsgService msgService;

    @Autowired
    private UidGenerator uidGenerator;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    void testInsert() {
        MsgBody body = new MsgBody();
        body.setMsgType(MsgType.PERSONAL);
        body.setMsgTopic("/test123");
        body.setMsgId(uidGenerator.nextId());
        body.setAppId(1321312313L);
        body.setSenderId(1L);
        body.setRcvrId(2L);
        body.setDetail("MsgDetail");
        body.setPayload("MsgPayload");
        body.setSendTime(new Date());
        msgService.insert(body);
        log.debug("insert:{}", body);
    }

    @Test
    void testUpdateStatus() {
        Long msgId = 41583215802732544L;
        msgService.updateStatus(msgId, MsgTransferStatus.REND);
    }

    @Test
    void getMsg() {
        MsgDTO msgDTO = msgService.getById(41591636664930304L, false);
        log.debug("{}", msgDTO);
    }

    @Test
    void testGtMsgtype() {
        Criteria criteria = Criteria
                .where(MsgEntityFieldName.TRANSFET_STATUS).gte(MsgTransferStatus.SENDING);
        List<MsgEntity> entities = mongoTemplate.find(Query.query(criteria), MsgEntity.class);
        entities.forEach(e -> log.debug("{}", e));
    }
}