package cn.wtk.mp.msg.manager.domain.msg.dispatcher;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.service.ServiceFiegnException;
import cn.wtk.mp.msg.manager.domain.msg.MsgHeader;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.MsgPushConverter;
import cn.wtk.mp.msg.manager.infrasturcture.exception.RelationException;
import cn.wtk.mp.msg.manager.infrasturcture.remote.feign.ConnectFiegn;
import cn.wtk.mp.msg.manager.infrasturcture.remote.feign.RelationFeign;
import cn.wtk.mp.msg.manager.infrasturcture.service.MsgPusher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/23
 */
@Slf4j
@Component
public class GroupRcvrMsgDispathcer extends AbstractMsgDispatcher {

    public GroupRcvrMsgDispathcer(MsgPusher msgPusher, ConnectFiegn connectFiegn, MsgPushConverter converter, RelationFeign relationFeign) {
        super(msgPusher, connectFiegn, converter, relationFeign);
    }

    @Override
    protected List<Long> getRevrIds(MsgHeader msgHeader) {
        Long groupId = msgHeader.getGroupId();
        try {
            return relationFeign.getGroupMembers(groupId);
        } catch (ServiceFiegnException e) {
            if (e.getResult().getCode() == ApiInfo.NOT_FOUND.getCode()) {
                throw new RelationException("群组不存在");
            } else {
                throw e;
            }
        }
    }
}
