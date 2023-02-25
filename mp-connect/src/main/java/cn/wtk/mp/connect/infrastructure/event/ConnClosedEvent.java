package cn.wtk.mp.connect.infrastructure.event;

import cn.wtk.mp.common.base.pojo.DomainEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023/2/24
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class ConnClosedEvent extends DomainEvent {
    Long connectorId;
    Long connId;

    public ConnClosedEvent(Long connectorId, Long deviceId) {
        super(deviceId);
        this.connectorId = connectorId;
        this.connId = deviceId;
    }
}
