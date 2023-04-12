package cn.wtk.mp.connect.domain.conn.server;

import cn.wtk.mp.connect.domain.conn.server.connector.Connector;
import cn.wtk.mp.connect.domain.conn.server.connector.connection.Connection;
import cn.wtk.mp.connect.domain.msg.connector.TransferMsg;
import cn.wtk.mp.connect.infrastructure.event.ConnectorCreatedEvent;
import cn.wtk.mp.connect.infrastructure.event.ConnectorRemovedEvent;
import com.mongodb.lang.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author wtk
 * @date 2023-01-06
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ServerConnContainer {

    private final Map<Long, Connector> connectors = new ConcurrentHashMap<>();
    private final ApplicationEventPublisher applicationEventPublisher;

    public void addConn(@NonNull Connection conn) {
        Long connectorId = conn.getConnectorId();
        log.debug("新增连接者：{}", connectorId);
        AtomicBoolean create = new AtomicBoolean(false);
        connectors.compute(connectorId, (k, connectorValue) -> {
            if (connectorValue == null) {
                connectorValue = new Connector(connectorId, conn.getAppId());
                create.set(true);
            }
            connectorValue.addConn(conn);
            return connectorValue;
        });
        if (create.get()) {
            // 新建的 connector
            applicationEventPublisher.publishEvent(new ConnectorCreatedEvent(connectorId));
        }
    }

    public Connection removeConn(@NonNull Long connectorId, @NonNull UUID connId) {
        // 要获取lambda表达式里的变量需要使用Atomic对象
        AtomicReference<Connection> connRef = new AtomicReference<>();
        AtomicBoolean connectorRemoved = new AtomicBoolean(false);
        log.debug("移除连接者：{}", connectorId);
        connectors.computeIfPresent(connectorId, (connectorIdKey, connectorValue) -> {
            Connection connection = connectorValue.removeConn(connId);
            connRef.set(connection);
            // 如果connector的连接数为0，则return null使其从map中移出
            if (connectorValue.getConnSize() == 0) {
                connectorRemoved.set(true);
                return null;
            } else {
                return connectorValue;
            }
        });
        if (connectorRemoved.get()) {
            applicationEventPublisher.publishEvent(new ConnectorRemovedEvent(connectorId));
        }
        return connRef.get();
    }

    public Connection getConn(@NonNull Long connectorId, @NonNull UUID connId) {
        Connector connector = connectors.get(connectorId);
        if (connector == null) {
            return null;
        }
        return connector.getConn(connId);
    }

    public Connector getConnector(@NonNull Long connectorId) {
        return connectors.get(connectorId);
    }

    /**
     * TODO 异常处理、重试
     * @param msgs
     */
    public void pushMsg(List<TransferMsg> msgs) {
        log.debug("进行消息推送：{}", msgs);
        for (TransferMsg msg : msgs) {
            for (Long rcvrId : msg.getRcvrIds()) {
                Connector connector = this.getConnector(rcvrId);
                if (connector != null) {
                    connector.pushMsg(msg);
                }
            }
        }
    }

    public int getConnetorSize() {
        return connectors.size();
    }
}
