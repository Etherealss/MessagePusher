package cn.wtk.mp.client.infrastructure.event;

import cn.wtk.mp.common.base.pojo.DomainEvent;

import java.util.UUID;

/**
 * @author wtk
 * @date 2023/3/29
 */
public class AuthEvent extends DomainEvent {
    public AuthEvent() {
        super(UUID.randomUUID());
    }
}
