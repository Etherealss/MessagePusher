package cn.wtk.mp.connect.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.connect.application.connector.ConnectorAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wtk
 * @date 2023-01-06
 */
@Slf4j
@RestController
@RequestMapping("/connectors/{connectorId}/devices")
@RequiredArgsConstructor
@ResponseAdvice
public class ConnectorDeviceController {
    private final ConnectorAppService connectorAppService;

    @GetMapping
    public Long registerDevices(@PathVariable Long connectorId) {
        return connectorAppService.registerConnectorDevice(connectorId);
    }
}
