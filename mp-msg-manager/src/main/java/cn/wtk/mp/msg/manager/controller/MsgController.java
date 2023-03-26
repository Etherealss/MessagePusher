package cn.wtk.mp.msg.manager.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.msg.manager.domain.msg.store.MsgService;
import cn.wtk.mp.msg.manager.infrasturcture.client.command.UpdateMsgStatusCommand;
import cn.wtk.mp.msg.manager.infrasturcture.client.dto.MsgDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Slf4j
@RestController
@RequestMapping("/msgs/{msgId}")
@RequiredArgsConstructor
@ResponseAdvice
public class MsgController {
    private final MsgService msgService;

    @GetMapping
    public MsgDTO getById(@PathVariable Long msgId) {
        return msgService.getById(msgId);
    }

    @PutMapping("/status")
    public void updateStatus(@PathVariable Long msgId, @RequestBody UpdateMsgStatusCommand command) {
        msgService.updateStatus(msgId, command.getStatus());
    }


}
