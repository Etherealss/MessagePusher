package cn.wtk.mp.msg.acceptor.domain.acceptor;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023-03-20
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AcceptorMsg {
    MsgHeader msgHeader;
    MsgBody msgBody;
}
