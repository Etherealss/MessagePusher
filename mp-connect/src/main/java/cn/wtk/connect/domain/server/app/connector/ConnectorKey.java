package cn.wtk.connect.domain.server.app.connector;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * @author wtk
 * @date 2023-01-11
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ConnectorKey {
    Long appId;
    Serializable connectorId;
}
