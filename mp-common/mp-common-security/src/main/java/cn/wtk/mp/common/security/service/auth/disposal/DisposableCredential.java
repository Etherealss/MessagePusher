package cn.wtk.mp.common.security.service.auth.disposal;


import cn.wtk.mp.common.security.service.auth.Credential;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * @author wtk
 * @date 2022-08-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DisposableCredential extends Credential {

    /**
     * 用于标识凭证所有者
     */
    Serializable key;

    /**
     * 应用id，用于区分不同应用
     */
    Long appId;

    public DisposableCredential(Serializable key, Long appId) {
        this.key = key;
        this.appId = appId;
    }
}
