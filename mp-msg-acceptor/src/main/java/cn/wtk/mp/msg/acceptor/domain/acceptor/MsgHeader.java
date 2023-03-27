package cn.wtk.mp.msg.acceptor.domain.acceptor;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.UUID;

/**
 * @author wtk
 * @date 2023/2/22
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MsgHeader {

    /**
     * 消息临时 ID，用于标识客户端发送的消息
     * @see MsgResendHandler 可用于去重
     * @see MsgSeqHandler 可用于保证消息有序
     */
    UUID tempId;

    /**
     * 上一条消息的临时 ID，用于保证消息有序。如果为空则视为首条消息，不检查有序性
     * @see MsgSeqHandler#handlerMsgSeq(MsgHeader)
     */
    UUID preMsgTempId;

    /**
     * 上一条消息的发送时间。如果发送时间超过服务器配置的时间则认为上一条消息已成功发送，不检查有序性。
     * 如果发送时间为空，同样不检查有序性（不管 preTempMsgId 的值）
     * @see MsgSeqHandler#handlerMsgSeq(MsgHeader)
     */
    Date preMsgSendTime;

    MsgType msgType;
    Boolean needPersistent;
    Boolean needRelationVerify;
    String relationTopic;
    Long senderId;
    Long rcvrId;
    Long groupId;
}
