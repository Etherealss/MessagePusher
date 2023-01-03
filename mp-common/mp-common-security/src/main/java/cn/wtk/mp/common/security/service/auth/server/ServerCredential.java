package cn.wtk.mp.common.security.service.auth.server;


import cn.wtk.mp.common.security.service.auth.Credential;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

/**
 * @author wtk
 * @date 2022-08-30
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServerCredential extends Credential {

    Long serverId;
    String serverName;
}
