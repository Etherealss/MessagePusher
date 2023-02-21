package cn.wtk.mp.msg.acceptor.domain.acceptor;

import cn.wtk.mp.common.msg.entity.Msg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 保证消息有序
 * @author wtk
 * @date 2023/2/21
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MsgSeqHandler {

    public void handlerMsgSeq(Msg msg) {

    }
}
