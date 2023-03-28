package cn.wtk.mp.auth.infrastructure.client.command;

import lombok.AccessLevel;
import lombok.Data;
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
public class CreateConnectCredentialCommand {
    @NotBlank
    @Pattern(regexp = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")
    String connectIp;
    @NotNull
    Integer connectPort;
}
