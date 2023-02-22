package cn.wtk.mp.msg.manager.infrasturcture.client.event;

import cn.wtk.mp.common.base.pojo.DomainEvent;
import cn.wtk.mp.common.msg.entity.Msg;
import lombok.Getter;


/**
 * @author wtk
 * @date 2023/2/21
 */
@Getter
public class ConsumeNewMsgEvent extends DomainEvent {
    private final Msg msg;

    public ConsumeNewMsgEvent(Msg msg) {
        super(msg);
        this.msg = msg;
    }
}