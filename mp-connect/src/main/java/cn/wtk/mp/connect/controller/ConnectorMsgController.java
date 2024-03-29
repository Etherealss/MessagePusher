package cn.wtk.mp.connect.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.connect.application.connector.MsgPushAppService;
import cn.wtk.mp.connect.infrastructure.client.command.MultiMsgPushCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wtk
 * @date 2023-02-25
 */
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@ResponseAdvice
public class ConnectorMsgController {

    private final MsgPushAppService msgPushAppService;

    @PostMapping("/list/connectors/msgs")
    public void pushMsg(@RequestBody @Validated MultiMsgPushCommand msg) {
        msgPushAppService.pushMsg(msg);
    }
}
