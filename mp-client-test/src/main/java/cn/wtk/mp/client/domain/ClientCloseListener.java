package cn.wtk.mp.client.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;


/**
 * @author wtk
 * @date 2023-01-06
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ClientCloseListener implements ApplicationListener<ContextClosedEvent> {
    private final NettyClient nettyClient;

    /**
     * 关闭 netty server
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (this.nettyClient != null) {
            this.nettyClient.close();
        }
        log.info("客户端关闭");
    }
}
