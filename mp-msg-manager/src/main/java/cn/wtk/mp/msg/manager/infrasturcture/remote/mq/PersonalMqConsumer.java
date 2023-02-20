package cn.wtk.mp.msg.manager.infrasturcture.remote.mq;

import cn.wtk.mp.common.msg.entity.PersonalMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Slf4j
@RequiredArgsConstructor
public class PersonalMqConsumer implements Consumer<PersonalMsg> {

    @Override
    public void accept(PersonalMsg personalMsg) {
        log.info("消费到 MQ 消息：{}", personalMsg);
    }
}
