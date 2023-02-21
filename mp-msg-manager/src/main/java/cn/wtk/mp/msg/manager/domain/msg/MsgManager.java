package cn.wtk.mp.msg.manager.domain.msg;

import cn.wtk.mp.common.msg.entity.Msg;
import cn.wtk.mp.msg.manager.infrasturcture.client.event.ConsumeNewMsgEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MsgManager {

    @EventListener(value = ConsumeNewMsgEvent.class)
    public void recvNewMsg(ConsumeNewMsgEvent event) {
        Msg msg = event.getMsg();
    }

}
