package cn.wtk.mp.connect.domain.msg.connector;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author wtk
 * @date 2023-02-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConnectorTransferMsg extends TransferMsg {
    List<Long> connectorRcvrIds;
}
