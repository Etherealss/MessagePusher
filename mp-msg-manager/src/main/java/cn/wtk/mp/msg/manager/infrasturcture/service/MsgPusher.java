package cn.wtk.mp.msg.manager.infrasturcture.service;

import cn.wtk.mp.msg.manager.infrasturcture.config.MsgPushRetryConfiguration;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.command.MultiMsgPushCommand;
import cn.wtk.mp.msg.manager.infrasturcture.remote.feign.ConnectFiegn;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023/2/23
 */
@Slf4j
@Component
public class MsgPusher {
    private final ConnectFiegn connectFiegn;
    private final RetryTemplate msgPushRetryTemplate;

    public MsgPusher(ConnectFiegn connectFiegn,
                     @Qualifier(MsgPushRetryConfiguration.BEAN_NAME)
                     RetryTemplate msgPushRetryTemplate) {
        this.connectFiegn = connectFiegn;
        this.msgPushRetryTemplate = msgPushRetryTemplate;
    }

    public boolean pushMsg(MultiMsgPushCommand msg, String rcvrIp, Integer rcvrPort) {
        try {
            msgPushRetryTemplate.execute((RetryCallback<Void, FeignException>) context -> {
                connectFiegn.pushMsg(msg, rcvrIp, rcvrPort);
                return null;
            }, context -> {
                log.warn("消息推送失败, rcvrIp: {}, rcvrPort: {}", rcvrIp, rcvrPort);
                return null;
            });
            return true;
        } catch (FeignException e) {
            log.warn("消息推送失败, rcvrIp: {}, rcvrPort: {}， 异常信息: {}",
                    rcvrIp, rcvrPort, e.getMessage());
            return false;
        }
    }
}
