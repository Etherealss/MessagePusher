package cn.wtk.mp.auth.domain.credential;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023-02-05
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenCredentialSpec {
    String tokenTopic;
    Long expireMs;
}
