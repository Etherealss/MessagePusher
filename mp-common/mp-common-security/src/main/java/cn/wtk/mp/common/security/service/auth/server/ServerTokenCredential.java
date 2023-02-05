package cn.wtk.mp.common.security.service.auth.server;


import cn.wtk.mp.common.security.service.auth.TokenCredential;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2022-08-30
 */
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServerTokenCredential extends TokenCredential {
    Long serverId;
    String serverName;
}
