package cn.wtk.mp.connect.infrastructure.client.command;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

/**
 * @author wtk
 * @date 2023/3/28
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateConnectAddressCommand {
    @NotBlank
    String connectorToken;
}
