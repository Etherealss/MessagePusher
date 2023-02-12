package cn.wtk.mp.msg.acceptor.application;

import cn.wtk.mp.common.base.pojo.Result;
import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.msg.acceptor.application.command.SendPersonalMsgCommand;
import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgAcceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    public Result<Void> sendPersonalMsg(SendPersonalMsgCommand command, Long appId) {
        PersonalMsg msg = new PersonalMsg();
        BeanUtils.copyProperties(command, msg);
        msg.setAppId(appId);
        return msgAcceptor.sendMsg(msg, command.getTempMsgId());
    }
}
