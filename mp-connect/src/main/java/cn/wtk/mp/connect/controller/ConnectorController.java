package cn.wtk.mp.connect.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import cn.wtk.mp.common.security.annotation.InternalAuth;
import cn.wtk.mp.connect.application.connector.ConnectorAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wtk
 * @date 2023-02-25
 */
@Slf4j
@RestController
@RequestMapping("/connectors")
@RequiredArgsConstructor
@ResponseAdvice
public class ConnectorController {
    private final ConnectorAppService connectorAppService;

    /**
     * 注册新的连接者
     * @return 连接者ID
     */
    @PostMapping
    @InternalAuth
    public Long registerConnector() {
        return connectorAppService.registerConnector();
    }
}
