package cn.wtk.mp.connect.infrastructure.client.command;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author wtk
 * @date 2023-03-20
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BatchConnectorIdQuery {
    List<Long> connectorIds;
}
