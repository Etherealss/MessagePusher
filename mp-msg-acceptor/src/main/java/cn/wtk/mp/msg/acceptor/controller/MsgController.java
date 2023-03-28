package cn.wtk.mp.msg.acceptor.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.annotation.InternalAuth;
import cn.wtk.mp.common.security.service.auth.server.ServerSecurityContextHolder;
import cn.wtk.mp.msg.acceptor.application.MsgAcceptorAppService;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.command.SendGroupMsgCommand;
import cn.wtk.mp.msg.acceptor.infrasturcture.client.command.SendPersonalMsgCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/personal")
    @InternalAuth
    public void sendPersonalMsg(@RequestBody @Validated SendPersonalMsgCommand command) {
        Long appId = ServerSecurityContextHolder.require().getServerId();
        msgAcceptorAppService.sendMsg(command, appId);
    }

    @PostMapping("/group")
    @InternalAuth
    public void sendGroupMsg(@RequestBody @Validated SendGroupMsgCommand command) {
        Long appId = ServerSecurityContextHolder.require().getServerId();
        msgAcceptorAppService.sendMsg(command, appId);
    }
}
