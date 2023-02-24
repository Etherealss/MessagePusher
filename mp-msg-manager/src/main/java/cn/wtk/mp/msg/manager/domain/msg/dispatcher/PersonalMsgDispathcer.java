package cn.wtk.mp.msg.manager.domain.msg.dispatcher;

import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.msg.manager.domain.msg.IMsgDispatcher;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.MsgConverter;
import cn.wtk.mp.msg.manager.infrasturcture.client.dto.MsgPushDTO;
import cn.wtk.mp.msg.manager.infrasturcture.service.MsgPusher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023/2/23
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class PersonalMsgDispathcer implements IMsgDispatcher<PersonalMsg> {

    private final MsgPusher msgPusher;
    private final MsgConverter converter;

    @Override
    public void doDispatch(PersonalMsg msg) {
        MsgPushDTO dto = converter.toPushDTO(msg);
    }
}
