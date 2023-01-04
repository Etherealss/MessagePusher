package cn.wtk.mp.common.security.service.auth.user;


import cn.wtk.mp.common.security.service.auth.Credential;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wtk
 * @date 2022-08-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCredential extends Credential {

    Serializable userId;

    /**
     * 应用id，用于区分不同应用下的userId
     */
    Long appId;

    /**
     * 登录时间
     */
    Date loginTime;

    public UserCredential(Serializable userId, Long appId) {
        this.userId = userId;
        this.appId = appId;
    }
}
