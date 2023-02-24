package cn.wtk.mp.connect.infrastructure.event;

import cn.wtk.mp.common.base.pojo.DomainEvent;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

/**
 * @author wtk
 * @date 2023/2/24
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class ConnClosedEvent extends DomainEvent {
    Long connectorId;
    UUID connId;

    public ConnClosedEvent(Long connectorId, UUID connId) {
        super(connId);
        this.connectorId = connectorId;
        this.connId = connId;
    }
}
