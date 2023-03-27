package cn.wtk.mp.msg.acceptor.infrasturcture.mq;

import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgBody;
import cn.wtk.mp.msg.acceptor.domain.acceptor.MsgHeader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wtk
 * @date 2023/3/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMsg {
    MsgHeader msgHeader;
    MsgBody msgBody;
}
