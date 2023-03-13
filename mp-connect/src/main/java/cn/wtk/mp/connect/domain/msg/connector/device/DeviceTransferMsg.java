package cn.wtk.mp.connect.domain.msg.connector.device;

import cn.wtk.mp.connect.domain.msg.connector.TransferMsg;
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
public class DeviceTransferMsg extends TransferMsg {
    List<Long> deviceRcvrIds;
}
