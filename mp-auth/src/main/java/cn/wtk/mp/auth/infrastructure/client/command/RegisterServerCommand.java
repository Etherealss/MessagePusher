package cn.wtk.mp.auth.infrastructure.client.command;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterServerCommand  {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9\\-]+$")
    String serverName;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*]{32}$")
    String secret;
}
