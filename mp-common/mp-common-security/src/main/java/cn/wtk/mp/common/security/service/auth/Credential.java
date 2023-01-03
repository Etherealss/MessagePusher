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
public abstract class Credential {

    String token;

    String refreshToken;

    /**
     * token 过期时间
     */
    Date tokenExpireAt;

    /**
     * refreshToken 过期时间
     */
    Date refreshTokenExpireAt;
}
