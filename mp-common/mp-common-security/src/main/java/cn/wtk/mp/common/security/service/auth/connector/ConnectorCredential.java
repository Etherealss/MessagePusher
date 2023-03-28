package cn.wtk.mp.common.security.service.auth.connector;


import cn.wtk.mp.common.security.service.auth.TokenCredential;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2022-08-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConnectorCredential extends TokenCredential {

    /**
     * 用于标识凭证所有者
     */
    Long connectorId;

    Long appId;

    String connectIp;

    Integer connectPort;

    public ConnectorCredential(Long connectorId, Long appId) {
        this.connectorId = connectorId;
        this.appId = appId;
    }
}
