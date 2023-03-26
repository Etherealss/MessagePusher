package cn.wtk.mp.msg.manager.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.msg.manager.domain.msg.store.personal.PersonalMsgService;
import cn.wtk.mp.msg.manager.infrasturcture.client.dto.PersonalMsgDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Slf4j
@RestController
@RequestMapping("/msgs/personal/{msgId}")
@RequiredArgsConstructor
@ResponseAdvice
public class PersonalMsgController {
    private final PersonalMsgService service;

    @GetMapping
    public PersonalMsgDTO getById(@PathVariable Long msgId) {
        return service.getById(msgId);
    }

}
