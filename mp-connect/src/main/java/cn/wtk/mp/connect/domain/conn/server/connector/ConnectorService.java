package cn.wtk.mp.connect.domain.conn.server.connector;

import cn.wtk.mp.common.base.uid.UidGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @date 2023-02-25
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectorService {

    private final UidGenerator uidGenerator;

    public Long createConnector() {
        // TODO 保存到数据库
        return uidGenerator.nextId();
    }
}
