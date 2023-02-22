package cn.wtk.mp.msg.acceptor.domain.acceptor;

import cn.wtk.mp.msg.acceptor.infrasturcture.config.MsgSeqProperties;
import cn.wtk.mp.msg.acceptor.infrasturcture.config.MsgSeqRetryConfiguration;
import cn.wtk.mp.msg.acceptor.infrasturcture.exception.MsgSeqRetryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

/**
 * 保证消息有序
 * 消息的整体有序是借助雪花算法的 ID 来实现的，而雪花算法并不保证 ID 的连续性
 * 所以没办法单纯借助雪花算法来保证消息不丢失和消息有序
 * 如果有的消息先发送但因为网络传输等原因后到达服务端，可能会导致消息的接受顺序与发送顺序不一致
 * 此时就需要进行处理
 * @author wtk
 * @date 2023/2/21
 */
@Slf4j
@Component
public class MsgSeqHandler {
    private static final String DEFAULT_VALUE = "1";

    private final RedisTemplate<String, String> redisTemplate;
    private final MsgSeqProperties msgSeqProperties;
    private final RetryTemplate msgSeqRetryTemplate;

    public MsgSeqHandler(RedisTemplate<String, String> redisTemplate,
                         MsgSeqProperties msgSeqProperties,
                         @Qualifier(MsgSeqRetryConfiguration.BEAN_NAME) RetryTemplate msgSeqRetryTemplate) {
        this.redisTemplate = redisTemplate;
        this.msgSeqProperties = msgSeqProperties;
        this.msgSeqRetryTemplate = msgSeqRetryTemplate;
    }

    /**
     * 处理消息有序的逻辑
     * @param spec
     * @return 可保证前一条 msg 已送达时返回 true。无法保证前一条消息已送达时返回 false。
     */
    public boolean handlerMsgSeq(MsgHandlerSpec spec) {
        boolean preMsgAccepted = checkPreMsgAccepted(spec);
        /*
        先等待前一条消息送达，如果确认已送达或超时，则会来到这里。
        此时设置当前消息的 tempId，后面的消息就可以与当前消息保证有序性
        TODO 问题：存在传递等待问题
         */
        setCurMsgTempId4Seq(spec);
        return preMsgAccepted;
    }

    private boolean checkPreMsgAccepted(MsgHandlerSpec spec) {
        if (isTimeLimitExceeded(spec.getPreMsgSendTime())) {
            return false;
        }
        try {
            Boolean result = msgSeqRetryTemplate.execute(retryContext -> {
                String tempIdKey = msgSeqProperties.getCacheKey() + ":" + spec.getTempId().toString();
                boolean exist = redisTemplate.opsForValue().get(tempIdKey) != null;
                if (exist) {
                    return true;
                } else {
                    throw new MsgSeqRetryException();
                }
            }, retryContext -> {
                log.info("重试次数耗尽，没有获取到上一条 msg 的 tempId。当前的 MsgHandlerSpec: {}", spec);
                return false;
            });
            return Boolean.TRUE.equals(result);
        } catch (MsgSeqRetryException e) {
            log.info("重试次数耗尽，没有获取到上一条 msg 的 tempId。当前的 MsgHandlerSpec: {}。异常信息：{}",
                    spec, e.getMessage());
        }
        return false;
    }


    private void setCurMsgTempId4Seq(MsgHandlerSpec spec) {
        String tempIdKey = msgSeqProperties.getCacheKey() + ":" + spec.getTempId().toString();
        redisTemplate.opsForValue().set(tempIdKey, DEFAULT_VALUE, Duration.ofMillis(msgSeqProperties.getExpireMs()));
    }

    /**
     * 两条消息的时间间隔超过配置的时间，则认为超时
     * @param preMsgSendTime
     * @return
     */
    private boolean isTimeLimitExceeded(Date preMsgSendTime) {
        long timeInterval = System.currentTimeMillis() - preMsgSendTime.getTime();
        return timeInterval > msgSeqProperties.getTimeLimit();
    }
}
