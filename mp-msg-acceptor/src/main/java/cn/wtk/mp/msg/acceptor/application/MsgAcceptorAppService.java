package cn.wtk.mp.msg.acceptor.application;

import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgAcceptor;
import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgBody;
import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgHeader;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.command.SendGroupMsgCommand;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.command.SendPersonalMsgCommand;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.converter.MsgConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public void sendMsg(SendPersonalMsgCommand command, Long appId) {
        MsgBody msgBody = msgConverter.toMsg(command);
        MsgHeader msgHeader = msgConverter.toMsgHeader(command);
        msgHeader.setNeedRelationVerify(StringUtils.hasText(command.getRelationTopic()));
        msgBody.setAppId(appId);
        msgBody.setMsgType(MsgType.PERSONAL);
        msgHeader.setMsgType(MsgType.PERSONAL);
        msgAcceptor.sendMsg(msgBody, msgHeader);
    }

    public void sendMsg(SendGroupMsgCommand command, Long appId) {
        MsgBody msgBody = msgConverter.toMsg(command);
        MsgHeader msgHeader = msgConverter.toMsgHeader(command);
        msgHeader.setNeedRelationVerify(true);
        msgBody.setAppId(appId);
        msgBody.setMsgType(MsgType.GROUP);
        msgHeader.setMsgType(MsgType.GROUP);
        msgAcceptor.sendMsg(msgBody, msgHeader);
    }
}
