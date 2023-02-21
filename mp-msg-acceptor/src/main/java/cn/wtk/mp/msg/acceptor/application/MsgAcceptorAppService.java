package cn.wtk.mp.msg.acceptor.application;

import cn.wtk.mp.common.base.pojo.Result;
import cn.wtk.mp.common.msg.entity.GroupMsg;
import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgAcceptor;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.command.SendGroupMsgCommand;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.command.SendPersonalMsgCommand;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.converter.MsgConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @date 2023-02-12
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MsgAcceptorAppService {

    private final MsgAcceptor msgAcceptor;
    private final MsgConverter msgConverter;

    public Result<Void> sendMsg(SendPersonalMsgCommand command, Long appId) {
        PersonalMsg msg = msgConverter.toMsg(command);
        msg.setAppId(appId);
        msg.setMsgType(MsgType.PERSONAL);
        return msgAcceptor.sendMsg(msg, command.getTempMsgId());
    }

    public Result<Void> sendMsg(SendGroupMsgCommand command, Long appId) {
        GroupMsg msg = msgConverter.toMsg(command);
        msg.setAppId(appId);
        msg.setMsgType(MsgType.GROUP);
        return msgAcceptor.sendMsg(msg, command.getTempMsgId());
    }

}
