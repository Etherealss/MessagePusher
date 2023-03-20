package cn.wtk.mp.connect.infrastructure.client.dto;

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
    Long connectorId;
    String ip;
    Integer port;
}
