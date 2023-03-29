package cn.wtk.mp.msg.manager.infrasturcture.service;

import cn.wtk.mp.msg.manager.infrasturcture.config.MsgPushRetryConfiguration;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.command.MultiMsgPushCommand;
import cn.wtk.mp.msg.manager.infrasturcture.remote.feign.ConnectFeign;
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
    private final ConnectFeign connectFeign;
    private final RetryTemplate msgPushRetryTemplate;

    public MsgPusher(ConnectFeign connectFeign,
                     @Qualifier(MsgPushRetryConfiguration.BEAN_NAME)
                     RetryTemplate msgPushRetryTemplate) {
        this.connectFeign = connectFeign;
        this.msgPushRetryTemplate = msgPushRetryTemplate;
    }

    public boolean pushMsg(MultiMsgPushCommand msg, String routeAddress) {
        try {
            msgPushRetryTemplate.execute((RetryCallback<Void, FeignException>) context -> {
                connectFeign.pushMsg(msg, routeAddress);
                return null;
            }, context -> {
                log.warn("消息推送失败, 请求地址：{}", routeAddress);
                return null;
            });
            return true;
        } catch (FeignException e) {
            log.warn("消息推送失败, 请求地址：{}， 异常信息: {}",
                    routeAddress, e.getMessage());
            return false;
        }
    }
}
