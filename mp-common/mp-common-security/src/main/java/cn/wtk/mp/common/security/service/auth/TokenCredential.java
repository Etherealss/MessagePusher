package cn.wtk.mp.common.security.service.auth;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * @author wtk
 * @date 2022-10-13
 */
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenCredential {

    String token;

    /**
     * token 过期时间
     */
    Date expireAt;

    /**
     * token 业务用途
     */
    String tokenTopic;
}
