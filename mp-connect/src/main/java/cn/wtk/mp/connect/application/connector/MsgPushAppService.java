package cn.wtk.mp.connect.application.connector;

import cn.wtk.mp.connect.domain.msg.connector.ConnectorMsgService;
import cn.wtk.mp.connect.domain.msg.connector.TransferMsg;
import cn.wtk.mp.connect.infrastructure.client.command.MultiMsgPushCommand;
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
public class MsgPushAppService {
    private final MsgConverter converter;
    private final ConnectorMsgService connectorMsgService;

    public void pushMsg(MultiMsgPushCommand command) {
        List<TransferMsg> msgs = converter.toConnectorTransferMsgs(command.getMsgs());
        connectorMsgService.pushMsg(msgs);
    }
}
