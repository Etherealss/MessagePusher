package cn.wtk.mp.msg.acceptor.domain.acceptor;

import cn.wtk.mp.common.msg.enums.MsgType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * @author wtk
 * @date 2023-03-20
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MsgBody {
    Long msgId;
    Long appId;
    Long senderId;
    String msgTopic;
    MsgType msgType;
    Date sendTime;
    Object payload;
    Object detail;
    Long rcvrId;
    Long groupId;
    String senderIp;
    Integer senderPort;
}
