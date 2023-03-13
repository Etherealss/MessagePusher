package cn.wtk.mp.connect.application.connector;

import cn.wtk.mp.connect.domain.msg.connector.ConnectorMsgService;
import cn.wtk.mp.connect.domain.msg.connector.ConnectorTransferMsg;
import cn.wtk.mp.connect.infrastructure.client.command.msg.connector.MultiConnectorMsgPushCommand;
import cn.wtk.mp.connect.infrastructure.client.converter.MsgConverter;
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
public class ConnectorMsgAppService {
    private final MsgConverter converter;
    private final ConnectorMsgService connectorMsgService;

    public void pushMsg(MultiConnectorMsgPushCommand command) {
        List<ConnectorTransferMsg> msgs = converter.toConnectorTransferMsgs(command.getMsgs());
        connectorMsgService.pushMsg(msgs);
    }
}
