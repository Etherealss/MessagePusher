package cn.wtk.mp.msg.acceptor.domain.acceptor;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.pojo.Result;
import cn.wtk.mp.common.msg.entity.Msg;
import cn.wtk.mp.msg.acceptor.domain.acceptor.resend.MsgResendHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author wtk
 * @date 2023/2/10
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class MsgAcceptor {

    private final MsgResendHandler msgResendHandler;

    public Result<Void> sendMsg(Msg msg, UUID tempMsgId) {
        if (msgResendHandler.handleMsgDuplicate(msg, tempMsgId)) {
            return new Result<>(true, ApiInfo.MSG_DUPILICATE);
        }
        return Result.ok();
    }
}
