package cn.wtk.mp.msg.manager.domain.msg.dispatcher;

import cn.wtk.mp.msg.manager.domain.msg.ManageMsg;
import cn.wtk.mp.msg.manager.domain.msg.MsgHeader;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.MsgConverter;
import cn.wtk.mp.msg.manager.infrasturcture.exception.RelationException;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.command.MsgPushCommand;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.command.MultiMsgPushCommand;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.connect.ConnectorAddressDTO;
import cn.wtk.mp.msg.manager.infrasturcture.remote.feign.ConnectFeign;
import cn.wtk.mp.msg.manager.infrasturcture.remote.feign.RelationFeign;
import cn.wtk.mp.msg.manager.infrasturcture.service.MsgPusher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/23
 */
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractMsgDispatcher {
    protected final MsgPusher msgPusher;
    protected final ConnectFeign connectFeign;
    protected final MsgConverter converter;
    protected final RelationFeign relationFeign;

    /**
     * 推送数据
     * @param msg 消息
     * @return 推送失败的 rcvrId
     */
    public void doDispatch(ManageMsg msg) {
        List<Long> revrIds = this.getRcvrIds(msg.getMsgHeader());
        List<ConnectorAddressDTO> addresses = getAddresses(revrIds);
        for (ConnectorAddressDTO address : addresses) {
            MsgPushCommand msgPushCommand = converter.toPushCommand(msg.getMsgBody());
            msgPushCommand.setRcvrIds(revrIds);
            this.pushMsg(address.getIp(), address.getPort(), msgPushCommand);
        }
    }

    protected abstract List<Long> getRcvrIds(MsgHeader msgHeader) throws RelationException;

    private List<ConnectorAddressDTO> getAddresses(List<Long> revrIds) {
        return connectFeign.getConnectorAddress(revrIds);
    }

    private void pushMsg(String ip, Integer port, MsgPushCommand msgPushCommand) {
        MultiMsgPushCommand multiMsgPushCommand = new MultiMsgPushCommand(msgPushCommand);
        msgPusher.pushMsg(multiMsgPushCommand, ip, port);
    }
}
