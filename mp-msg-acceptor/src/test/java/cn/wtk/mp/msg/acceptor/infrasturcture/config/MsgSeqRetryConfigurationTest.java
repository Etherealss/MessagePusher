package cn.wtk.mp.msg.acceptor.infrasturcture.config;

import cn.wtk.mp.msg.acceptor.infrasturcture.exception.MsgSeqRetryException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.retry.support.RetryTemplate;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j(topic = "test")
@DisplayName("MsgSeqRetryConfigurationTest测试")
@SpringBootTest
class MsgSeqRetryConfigurationTest {

    @Autowired
    @Qualifier(MsgSeqRetryConfiguration.BEAN_NAME)
    private RetryTemplate msgSeqRetryTemplate;

    @Test
    public void retryTemplateTest() {
        Boolean testResult = null;
        try {
            Boolean result = msgSeqRetryTemplate.execute(retryContext -> {
                log.info("重试任务进行中...");
                boolean exist = this.getFalse();
                if (exist) {
                    return true;
                } else {
                    throw new MsgSeqRetryException();
                }
            }, retryContext -> {
                log.info("重试次数耗尽，没有获取到上一条 msg 的 tempId");
                return false;
            });
            testResult = result;
        } catch (MsgSeqRetryException e) {
            log.info("重试次数耗尽，没有获取到上一条 msg 的 tempId，异常：{}", e.getMessage());
        }
        log.info("testResult: {}", testResult);
    }

    private Boolean getFalse() {
        return false;
    }
}