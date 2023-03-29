package cn.wtk.mp.client.infrastructure.remote.command;

import cn.wtk.mp.client.infrastructure.remote.dto.ConnectorAddressDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CreateConnectCredentialCommand {
    @NotBlank
    @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")
    String connectIp;
    @NotNull
    Integer connectPort;

    public CreateConnectCredentialCommand(ConnectorAddressDTO address) {
        this.connectIp = address.getIp();
        this.connectPort = address.getPort();
    }
}
