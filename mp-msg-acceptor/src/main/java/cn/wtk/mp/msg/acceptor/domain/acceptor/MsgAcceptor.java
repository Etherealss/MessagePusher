package cn.wtk.mp.msg.acceptor.domain.acceptor;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.BaseException;
import cn.wtk.mp.common.base.pojo.Result;
import cn.wtk.mp.common.msg.entity.Msg;
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
    private final MsgRelationVerifier msgRelationVerifier;

    public Result<Void> sendMsg(Msg msg, UUID tempMsgId) {
        if (msgResendHandler.handleMsgDuplicate(msg, tempMsgId)) {
            return new Result<>(true, ApiInfo.MSG_DUPILICATE);
        }
        if (!msgRelationVerifier.doVerify(msg)) {
            throw new BaseException(ApiInfo.MSG_REALTION_MISMATCH,
                    "发送方与接收方关系不匹配，无法发送。"
            );
        }
        return Result.ok();
    }
}
