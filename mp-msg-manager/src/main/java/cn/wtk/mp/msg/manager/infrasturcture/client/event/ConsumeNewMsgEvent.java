package cn.wtk.mp.msg.manager.infrasturcture.client.event;

import cn.wtk.mp.common.base.pojo.DomainEvent;
import cn.wtk.mp.common.msg.entity.Msg;
import lombok.Getter;
import org.springframework.kafka.support.Acknowledgment;


/**
 * @author wtk
 * @date 2023/2/21
 */
@Getter
public class ConsumeNewMsgEvent extends DomainEvent {
    private final Msg msg;
    private final Acknowledgment ack;

    public ConsumeNewMsgEvent(Msg msg, Acknowledgment ack) {
        super(msg);
        this.msg = msg;
        this.ack = ack;
    }
}
