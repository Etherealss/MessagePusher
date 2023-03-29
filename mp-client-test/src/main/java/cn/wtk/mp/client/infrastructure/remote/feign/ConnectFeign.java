package cn.wtk.mp.client.infrastructure.remote.feign;

import cn.wtk.mp.client.infrastructure.remote.dto.ConnectorAddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author wtk
 * @date 2023/3/29
 */
@FeignClient(
        value = "mp-connect",
        path = "/connect"
)
public interface ConnectFeign {

    @PutMapping("/connectors/{connectorId}/addresses/connect")
    ConnectorAddressDTO updateConnectAddress(@PathVariable Long connectorId);
}
