package cn.wtk.mp.connect.domain.msg.connector;

import cn.wtk.mp.connect.domain.msg.connector.device.DeviceMsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wtk
 * @date 2023-02-25
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectorMsgService {

    private final DeviceMsgService deviceMsgService;

    public void pushMsg(List<ConnectorTransferMsg> msgs) {

    }
}
