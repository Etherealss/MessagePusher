package cn.wtk.mp.client.infrastructure.remote.feign;

import cn.wtk.mp.client.infrastructure.remote.command.CreateConnectCredentialCommand;
import cn.wtk.mp.common.security.service.auth.connector.ConnectorCredential;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author wtk
 * @date 2023-01-09
 */
@FeignClient(
        value = "mp-auth",
        path = "/auth"
)
public interface AuthFeign {
    @PostMapping("/apps/{appId}/connectors/{connectorId}/credentials")
    ConnectorCredential createCredential(
            @PathVariable Long appId,
            @PathVariable Long connectorId,
            @RequestBody @Validated CreateConnectCredentialCommand command);

}
