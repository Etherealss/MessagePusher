package cn.wtk.mp.client.domain;

import cn.wtk.mp.client.infrastructure.pojo.dto.AckMsg;
import cn.wtk.mp.client.infrastructure.utils.MessageSendUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Scanner;

/**
 * @author wtk
 * @date 2023/3/27
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MsgSendHandler {
    private final NettyClient nettyClient;

    @PostConstruct
    public void startWriting() {
        new Thread(() -> {
            log.info("请输入：\n");
            Scanner sc = new Scanner(System.in);
            try {
                while (sc.hasNext()) {
                    String s = sc.nextLine();
                    AckMsg msg = new AckMsg(1L, 2L, 3L, new Date());
                    MessageSendUtil.send(nettyClient.getChannel(), msg);
                }
            } catch (Exception e) {
                log.warn("", e);
            }
        }).start();
    }
}
