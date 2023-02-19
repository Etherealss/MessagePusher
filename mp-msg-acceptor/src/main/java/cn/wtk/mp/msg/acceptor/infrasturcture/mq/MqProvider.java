package cn.wtk.mp.msg.acceptor.infrasturcture.mq;

import cn.wtk.mp.common.msg.entity.GroupMsg;
import cn.wtk.mp.common.msg.entity.PersonalMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MqProvider {
    private final StreamBridge streamBridge;

    public void sendPersonal(PersonalMsg msg) {
        streamBridge.send(StreamBindingName.PERSONAL_MSG, msg);
    }

    public void sendPersonal(GroupMsg msg) {
        streamBridge.send(StreamBindingName.GROUP_MSG, msg);
    }

}
