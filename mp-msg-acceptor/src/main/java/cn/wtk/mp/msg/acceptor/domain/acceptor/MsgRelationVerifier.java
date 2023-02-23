package cn.wtk.mp.msg.acceptor.domain.acceptor;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.common.base.exception.BaseException;
import cn.wtk.mp.common.msg.entity.GroupMsg;
import cn.wtk.mp.common.msg.entity.Msg;
import cn.wtk.mp.common.msg.entity.PersonalMsg;
import cn.wtk.mp.msg.acceptor.infrasturcture.remote.feign.RelationFeign;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author wtk
 * @date 2023/2/19
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MsgRelationVerifier {
    private final RelationFeign relationFeign;

    public boolean doVerify(Msg msg) {
        try {
            // TODO 责任链模式
            if (msg instanceof PersonalMsg) {
                return verifySubRelation((PersonalMsg) msg);
            } else if (msg instanceof GroupMsg) {
                return verifyGroupRelation((GroupMsg)msg);
            }
        } catch (FeignException feignException) {
            Throwable cause = feignException.getCause();
            log.info("验证关系时出现异常：{}", cause.getMessage());
            throw new BaseException(ApiInfo.MSG_REALTION_MISMATCH,
                    "发送方与接收方关系不匹配，无法发送。详细信息：" + cause.getMessage(),
                    cause
            );
        }
        return true;
    }

    private boolean verifySubRelation(PersonalMsg msg) {
        if (needVerifySubRelation(msg)) {
            // A给 B 发消息，那么 A 要先订阅 B，则现在要检查：B 的订阅列表里有没有 A
            return relationFeign.checkSubRelation(
                    msg.getRcvrId(),
                    msg.getSenderId(),
                    msg.getRelationTopic()
            );
        }
        // 如果不需要验证关系，那就直接返回 true，认为关系验证通过
        return true;
    }

    private boolean needVerifySubRelation(PersonalMsg msg) {
        return StringUtils.hasText(msg.getRelationTopic());
    }

    private boolean verifyGroupRelation(GroupMsg msg) {
        return relationFeign.checkMember(msg.getGroupId(), msg.getSenderId());
    }
}
