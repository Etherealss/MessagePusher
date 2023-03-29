package cn.wtk.mp.msg.manager.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.msg.manager.domain.msg.store.MsgService;
import cn.wtk.mp.msg.manager.infrasturcture.client.command.UpdateMsgStatusCommand;
import cn.wtk.mp.msg.manager.infrasturcture.client.dto.MsgDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@ResponseAdvice
public class MsgController {
    private final MsgService msgService;

    @GetMapping("/msgs/{msgId}")
    public MsgDTO getById(@PathVariable Long msgId, @RequestParam Boolean unread) {
        return msgService.getById(msgId, unread);
    }

    @PutMapping("/msgs/{msgId}/status")
    public void updateStatus(@PathVariable Long msgId, @RequestBody UpdateMsgStatusCommand command) {
        msgService.updateStatus(msgId, command.getStatus());
    }

    @GetMapping("/pages/msgs")
    public List<MsgDTO> page(@RequestParam(required = false) Long senderId,
                             @RequestParam Long rcvrId,
                             @RequestParam Long cursorMsgId,
                             @RequestParam(required = false) String msgTopic,
                             @RequestParam(required = false, defaultValue = "false") Boolean unread,
                             @RequestParam(required = false, defaultValue = "10") Integer size) {
        return this.msgService.page(senderId, rcvrId, cursorMsgId, msgTopic, unread, size);
    }
}
