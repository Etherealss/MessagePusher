package cn.wtk.mp.msg.manager.infrasturcture.remote.dto.connect;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023/2/24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConnectorAddressDTO {
    String ip;
    Integer port;
}
