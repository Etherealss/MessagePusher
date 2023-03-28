package cn.wtk.mp.auth.infrastructure.client.command;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterServerCommand  {
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9\\-]+$")
    String serverName;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{32}$")
    String secret;
}
