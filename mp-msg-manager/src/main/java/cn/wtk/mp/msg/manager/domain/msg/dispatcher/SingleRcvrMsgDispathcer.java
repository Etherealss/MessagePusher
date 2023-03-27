package cn.wtk.mp.msg.manager.domain.msg.dispatcher;

import cn.wtk.mp.msg.manager.domain.msg.MsgHeader;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.MsgConverter;
import cn.wtk.mp.msg.manager.infrasturcture.exception.RelationException;
import cn.wtk.mp.msg.manager.infrasturcture.remote.feign.ConnectFiegn;
import cn.wtk.mp.msg.manager.infrasturcture.remote.feign.RelationFeign;
import cn.wtk.mp.msg.manager.infrasturcture.service.MsgPusher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author wtk
 * @date 2023/2/23
 */
@Slf4j
@Component
public class SingleRcvrMsgDispathcer extends AbstractMsgDispatcher {


    public SingleRcvrMsgDispathcer(MsgPusher msgPusher, ConnectFiegn connectFiegn, MsgConverter converter, RelationFeign relationFeign) {
        super(msgPusher, connectFiegn, converter, relationFeign);
    }

    @Override
    protected List<Long> getRcvrIds(MsgHeader msgHeader) {
        List<Long> rcvrIds = Collections.singletonList(msgHeader.getRcvrId());
        if (!msgHeader.getNeedRelationVerify()) {
            return rcvrIds;
        }
        Boolean success = super.relationFeign.checkSubRelation(
                msgHeader.getSenderId(),
                msgHeader.getRcvrId(),
                msgHeader.getRelationTopic()
        );
        if (Boolean.TRUE.equals(success)) {
            return rcvrIds;
        } else {
            throw new RelationException("关系不匹配");
        }
    }
}
