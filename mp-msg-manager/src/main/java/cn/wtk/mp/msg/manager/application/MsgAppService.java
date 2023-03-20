package cn.wtk.mp.msg.manager.application;

import cn.wtk.mp.common.msg.enums.MsgTransferStatus;
import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.manager.domain.msg.ManageMsg;
import cn.wtk.mp.msg.manager.domain.msg.dispatcher.GroupRcvrMsgDispathcer;
import cn.wtk.mp.msg.manager.domain.msg.dispatcher.SingleRcvrMsgDispathcer;
import cn.wtk.mp.msg.manager.domain.msg.store.MsgService;
import cn.wtk.mp.msg.manager.infrasturcture.client.event.ConsumeNewMsgEvent;
import cn.wtk.mp.msg.manager.infrasturcture.exception.RelationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MsgAppService {
    private final MsgService msgService;

    private final SingleRcvrMsgDispathcer singleRcvrMsgDispathcer;
    private final GroupRcvrMsgDispathcer groupRcvrMsgDispathcer;

    @EventListener(value = ConsumeNewMsgEvent.class)
    public void recvNewMsg(ConsumeNewMsgEvent event) {
        ManageMsg msg = event.getMsg();
        // TODO 数据库存储失败
        this.insertMsg(msg);
        // 存储成功（或不需要存储），消费 MQ 事件
        // TODO 不存在持久化的消息保存到临时消息表中，保证消息不丢失
        Acknowledgment ack = event.getAck();
        ack.acknowledge();
        dispatch(msg);
    }

    private void insertMsg(ManageMsg msg) {
        if (!msg.getMsgHeader().getNeedPersistent()) {
            return;
        }
        msgService.insert(msg);
    }

    private void dispatch(ManageMsg msg) {
        try {
            if (MsgType.PERSONAL.equals(msg.getMsgHeader().getMsgType())) {
                singleRcvrMsgDispathcer.doDispatch(msg);
            } else {
                groupRcvrMsgDispathcer.doDispatch(msg);
            }
        } catch (RelationException e) {
            log.info("关系异常，不允许发送：{}", e.getMessage());
            msgService.updateStatus(msg.getMsgBody().getMsgId(), MsgTransferStatus.REJECT);
        } catch (Exception e) {
            log.warn("消息推送失：{}", e.getMessage());
            msgService.updateStatus(msg.getMsgBody().getMsgId(), MsgTransferStatus.FAIL);
        }
    }
}
