package cn.wtk.mp.msg.acceptor.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.service.auth.server.ServerSecurityContextHolder;
import cn.wtk.mp.msg.acceptor.application.MsgAcceptorAppService;
import cn.wtk.mp.msg.acceptor.infrasturcture.command.SendPersonalMsgCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wtk
 * @date 2023-02-12
 */
@Slf4j
@RestController
@RequestMapping("/msgs")
@RequiredArgsConstructor
@ResponseAdvice
public class MsgController {

    private final MsgAcceptorAppService msgAcceptorAppService;

    public void sendPersonalMsg(@RequestBody @Validated SendPersonalMsgCommand command) {
        Long appId = ServerSecurityContextHolder.require().getServerId();
        msgAcceptorAppService.sendPersonalMsg(command, appId);
    }
}
