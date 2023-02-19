package cn.wtk.mp.msg.manager.infrasturcture.remote.mq;

import cn.wtk.mp.common.msg.entity.GroupMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class GroupMqComsumer implements Consumer<GroupMsg> {

    @Override
    public void accept(GroupMsg groupMsg) {
        log.info("消费到 MQ 消息：{}", groupMsg);
    }
}
