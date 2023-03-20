package cn.wtk.mp.msg.manager.infrasturcture.client.event;

import cn.wtk.mp.common.base.pojo.DomainEvent;
import cn.wtk.mp.msg.manager.domain.msg.ManageMsg;
import lombok.Getter;
import org.springframework.kafka.support.Acknowledgment;


/**
 * @author wtk
 * @date 2023/2/21
 */
@Getter
public class ConsumeNewMsgEvent extends DomainEvent {
    private final ManageMsg msg;
    private final Acknowledgment ack;

    public ConsumeNewMsgEvent(ManageMsg msg, Acknowledgment ack) {
        super(ack);
        this.msg = msg;
        this.ack = ack;
    }
}
