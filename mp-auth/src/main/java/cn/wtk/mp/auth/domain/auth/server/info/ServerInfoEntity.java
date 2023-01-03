package cn.wtk.mp.auth.domain.auth.server.info;

import cn.wtk.mp.common.database.pojo.entity.IdentifiedEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author wtk
 * @date 2022-12-28
 */
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document("server_info")
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ServerInfoEntity extends IdentifiedEntity {
    String serverName;
    String secret;
}

