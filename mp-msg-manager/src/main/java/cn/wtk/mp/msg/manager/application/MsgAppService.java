package cn.wtk.mp.msg.manager.application;

import cn.wtk.mp.common.msg.entity.GroupMsg;
import cn.wtk.mp.common.msg.entity.Msg;
import cn.wtk.mp.common.msg.entity.NotifMsg;
import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.msg.manager.domain.msg.dispatcher.GroupRcvrMsgDispathcer;
import cn.wtk.mp.msg.manager.domain.msg.dispatcher.SingleRcvrMsgDispathcer;
import cn.wtk.mp.msg.manager.domain.msg.store.group.GroupMsgService;
import cn.wtk.mp.msg.manager.domain.msg.store.notif.NotifMsgService;
import cn.wtk.mp.msg.manager.domain.msg.store.personal.PersonalMsgService;
import cn.wtk.mp.msg.manager.infrasturcture.client.event.ConsumeNewMsgEvent;
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

    private final PersonalMsgService personalMsgService;
    private final GroupMsgService groupMsgService;
    private final NotifMsgService notifMsgService;

    private final SingleRcvrMsgDispathcer singleRcvrMsgDispathcer;
    private final GroupRcvrMsgDispathcer groupRcvrMsgDispathcer;

    @EventListener(value = ConsumeNewMsgEvent.class)
    public void recvNewMsg(ConsumeNewMsgEvent event) {
        Msg msg = event.getMsg();
        // TODO 数据库存储失败
        if (msg.getPersistent()) {
            this.insertMsg(msg);
        }
        // 存储成功（或不需要存储），消费 MQ 事件
        // TODO 不存在持久化的消息保存到临时消息表中，保证消息不丢失
        Acknowledgment ack = event.getAck();
        ack.acknowledge();
        try {
            dispatch(msg);
        } catch (Throwable throwable) {
            // TODO 全局异常处理
            log.warn("推送消息出现异常：{}", throwable.getMessage());
        }
    }

    private void insertMsg(Msg msg) {
        switch (msg.getMsgType()) {
            case PERSONAL:
                personalMsgService.insert((PersonalMsg) msg);
                break;
            case GROUP:
                groupMsgService.insert((GroupMsg) msg);
                break;
            case DEVICE:
                notifMsgService.insert((NotifMsg) msg);
                break;
            default:
        }
    }

    private void dispatch(Msg msg) {
        switch (msg.getMsgType()) {
            case PERSONAL:
                singleRcvrMsgDispathcer.doDispatch((PersonalMsg) msg);
                break;
            case GROUP:
                groupRcvrMsgDispathcer.doDispatch((GroupMsg) msg);
                break;
            case DEVICE:
                break;
            default:
        }
    }
}
