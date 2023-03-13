package cn.wtk.mp.connect.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.connect.infrastructure.client.command.msg.device.MultiDeviceMsgPushCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wtk
 * @date 2023-01-06
 */
@Slf4j
@RestController
@RequestMapping("/connectors/{connectorId}/devices/{deviceId}/msgs")
@RequiredArgsConstructor
@ResponseAdvice
public class ConnectorDeviceMsgController {

    @PostMapping
    public void pushMsg(@RequestBody @Validated MultiDeviceMsgPushCommand msg) {

    }
}
