package cn.wtk.mp.common.security.service.auth.user;


import cn.wtk.mp.common.security.service.auth.Credential;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

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

    Integer userId;

    String username;

    /**
     * 登录时间
     */
    Date loginTime;

    /**
     * 用户权限
     */
    Set<String> permissions;

    /**
     * 用户角色
     */
    Set<String> roles;

    public UserCredential(Integer userId, String username, Set<String> permissions, Set<String> roles) {
        this.userId = userId;
        this.username = username;
        this.permissions = permissions;
        this.roles = roles;
    }
}
