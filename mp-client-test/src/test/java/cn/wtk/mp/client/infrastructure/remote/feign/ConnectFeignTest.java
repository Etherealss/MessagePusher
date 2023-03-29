package cn.wtk.mp.client.infrastructure.remote.feign;

import cn.wtk.mp.client.infrastructure.remote.dto.ConnectorAddressDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j(topic = "test")
@DisplayName("ConnectFeignTest测试")
@SpringBootTest
class ConnectFeignTest {
    @Autowired
    private ConnectFeign connectFeign;
    @Test
    void testUpdateConnectAddress() {
        ConnectorAddressDTO connectorAddressDTO = connectFeign.updateConnectAddress(123L);
        log.debug("connectorAddressDTO={}", connectorAddressDTO);
    }
}