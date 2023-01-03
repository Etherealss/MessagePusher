package cn.wtk.mp.uid.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.uid.domain.AllocUidCommand;
import cn.wtk.mp.uid.domain.UidGenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wtk
 * @date 2023-01-02
 */
@Slf4j
@RestController
@RequestMapping("/uids")
@RequiredArgsConstructor
@ResponseAdvice
public class UidAllocateController {
    private final UidGenerateService uidGenerateService;

    @PostMapping
    public Long allocUid() {
        return uidGenerateService.allocUid();
    }

    @PostMapping("/batch")
    public List<Long> allocUids(@RequestBody AllocUidCommand command) {
        return uidGenerateService.allocUids(command);
    }
}
