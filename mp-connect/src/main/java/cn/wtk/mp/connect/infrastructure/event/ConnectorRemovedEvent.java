package cn.wtk.mp.connect.infrastructure.event;

import cn.wtk.mp.common.base.pojo.DomainEvent;
import lombok.Getter;

/**
 * @author wtk
 * @date 2023-01-20
 */
@Getter
public class ConnectorRemovedEvent extends DomainEvent {
    Long connectorId;
    public ConnectorRemovedEvent(Long connectorId) {
        super(connectorId);
        this.connectorId = connectorId;
    }
}
