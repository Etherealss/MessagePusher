package cn.wtk.mp.msg.manager.domain.msg.dispatcher;

import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.msg.manager.domain.msg.IMsgDispatcher;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.MsgConverter;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.command.MsgPushCommand;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.command.MultiMsgPushCommand;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.connect.ConnectorAddressDTO;
import cn.wtk.mp.msg.manager.infrasturcture.remote.feign.ConnectFiegn;
import cn.wtk.mp.msg.manager.infrasturcture.service.MsgPusher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author wtk
 * @date 2023/2/23
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SingleRcvrMsgDispathcer implements IMsgDispatcher<PersonalMsg> {

    private final MsgPusher msgPusher;
    private final ConnectFiegn connectFiegn;
    private final MsgConverter converter;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> doDispatch(PersonalMsg msg) {
        Long rcvrId = msg.getRcvrId();
        MsgPushCommand msgPushCommand = converter.toPushDTO(msg);
        msgPushCommand.setRcvrIds(Collections.singletonList(rcvrId));
        MultiMsgPushCommand multiMsgPushCommand = new MultiMsgPushCommand(msgPushCommand);
        ConnectorAddressDTO rcvrAddress = connectFiegn.getConnectorAddress(rcvrId);
        boolean successful = msgPusher.pushMsg(multiMsgPushCommand, rcvrAddress.getIp(), rcvrAddress.getPort());
        if (successful) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(rcvrId);
        }
    }
}
