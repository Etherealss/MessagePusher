package cn.wtk.mp.connect.controller;

import cn.wtk.mp.common.base.web.ResponseAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
