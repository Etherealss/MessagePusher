package cn.wtk.mp.auth.domain.auth.user.credential;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * @author wtk
 * @date 2023-01-04
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAuthCommand {
    Serializable userId;
    Long appId;
}
