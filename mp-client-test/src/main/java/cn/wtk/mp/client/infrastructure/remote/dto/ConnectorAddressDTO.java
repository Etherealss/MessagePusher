package cn.wtk.mp.client.infrastructure.remote.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023/2/24
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ConnectorAddressDTO {
    Long connectorId;
    String ip;
    Integer port;

    public ConnectorAddressDTO(Long connectorId, String address) {
        String[] info = address.split(":");
        if (info.length != 2) {
            throw new RuntimeException("address: " + address + " 格式不正确");
        }
        this.setConnectorId(connectorId);
        this.setIp(info[0]);
        this.setPort(Integer.parseInt(info[1]));
        this.setConnectorId(connectorId);
    }
}