package cn.wtk.mp.connect.infrastructure.event;

import cn.wtk.mp.common.base.pojo.DomainEvent;
import cn.wtk.mp.connect.domain.server.app.connector.Connector;
import lombok.Getter;

/**
 * @author wtk
 * @date 2023-01-20
 */
@Getter
public class ConnectorRemovedEvent extends DomainEvent {
    Connector connector;
    public ConnectorRemovedEvent(Connector connector) {
        super(connector);
        this.connector = connector;
    }
}
