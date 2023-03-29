package cn.wtk.mp.client.infrastructure.remote.feign;

import cn.wtk.mp.client.infrastructure.remote.command.CreateConnectCredentialCommand;
import cn.wtk.mp.common.security.service.auth.connector.ConnectorCredential;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j(topic = "test")
@DisplayName("AuthFeignTest测试")
@SpringBootTest
class AuthFeignTest {
    @Autowired
    private AuthFeign authFeign;

    @Test
    void testCreateCredential() {
        ConnectorCredential credential = authFeign.createCredential(
                41462388909805568L,
                123L,
                new CreateConnectCredentialCommand("127.0.0.1", 2242)
        );
        log.info("{}", credential);
    }
}