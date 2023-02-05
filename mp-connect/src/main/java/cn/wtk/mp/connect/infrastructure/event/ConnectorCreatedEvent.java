package cn.wtk.mp.connect.infrastructure.event;

import cn.wtk.mp.common.base.pojo.DomainEvent;
import cn.wtk.mp.connect.domain.server.connector.Connector;
import lombok.Getter;

/**
 * @author wtk
 * @date 2023-01-20
 */
@Getter
public class ConnectorCreatedEvent extends DomainEvent {
    Connector connector;
    public ConnectorCreatedEvent(Connector connector) {
        super(connector);
        this.connector = connector;
    }
}
