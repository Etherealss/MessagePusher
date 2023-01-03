package cn.wtk.mp.auth.domain.auth.server.info;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterServerCommand  {
    @NotEmpty
    String serverName;
    @NotEmpty
    String secret;
}
