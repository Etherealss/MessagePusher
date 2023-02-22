package cn.wtk.mp.msg.manager.application;

import cn.wtk.mp.common.msg.entity.GroupMsg;
import cn.wtk.mp.common.msg.entity.Msg;
import cn.wtk.mp.common.msg.entity.NotifMsg;
import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.msg.manager.domain.msg.MsgManager;
import cn.wtk.mp.msg.manager.domain.msg.group.GroupMsgService;
import cn.wtk.mp.msg.manager.domain.msg.notif.NotifMsgService;
import cn.wtk.mp.msg.manager.domain.msg.personal.PersonalMsgService;
import cn.wtk.mp.msg.manager.infrasturcture.client.event.ConsumeNewMsgEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MsgAppService {

    private final MsgManager msgManager;
    private final GroupMsgService groupMsgService;
    private final PersonalMsgService personalMsgService;
    private final NotifMsgService notifMsgService;

    @EventListener(value = ConsumeNewMsgEvent.class)
    public void recvNewMsg(ConsumeNewMsgEvent event) {
        Msg msg = event.getMsg();
        switch (msg.getMsgType()) {
            case PERSONAL:
                personalMsgService.consume((PersonalMsg) msg);
                break;
            case GROUP:
                groupMsgService.consume((GroupMsg) msg);
                break;
            case NOTIF:
                notifMsgService.consume((NotifMsg) msg);
                break;
            default:
        }
    }
}
