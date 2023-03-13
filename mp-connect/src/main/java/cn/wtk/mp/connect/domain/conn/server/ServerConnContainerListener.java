package cn.wtk.mp.connect.domain.conn.server;

import cn.wtk.mp.connect.infrastructure.event.ConnClosedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023/2/24
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ServerConnContainerListener {

    private final ServerConnContainer serverConnContainer;

    @EventListener(ConnClosedEvent.class)
    public void removeConn(ConnClosedEvent event) {
        serverConnContainer.removeConn(event.getConnectorId(), event.getConnId());
    }
}
