package cn.wtk.mp.msg.manager.domain.msg.dispatcher;

import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.msg.manager.domain.msg.IMsgDispatcher;
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

    @Override
    public void doDispatch(PersonalMsg msg) {

    }
}
