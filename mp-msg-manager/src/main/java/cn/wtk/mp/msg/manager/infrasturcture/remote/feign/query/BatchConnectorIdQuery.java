package cn.wtk.mp.msg.manager.infrasturcture.remote.feign.query;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author wtk
 * @date 2023-03-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BatchConnectorIdQuery {
    List<Long> connectorId;
}
