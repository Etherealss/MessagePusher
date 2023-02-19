package cn.wtk.mp.common.security.service.auth.server;


import cn.wtk.mp.common.security.service.auth.TokenCredential;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2022-08-30
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServerTokenCredential extends TokenCredential {
    Long serverId;
    String serverName;
}
