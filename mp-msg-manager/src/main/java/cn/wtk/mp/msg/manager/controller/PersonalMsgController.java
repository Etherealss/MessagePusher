package cn.wtk.mp.msg.manager.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.msg.manager.domain.msg.store.personal.PersonalMsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}
