package cn.wtk.mp.common.base.pojo;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

/**
 * @author wtk
 */
@Getter
public abstract class DomainEvent extends ApplicationEvent {
    private final UUID id;

    public DomainEvent(Object source) {
        super(source);
        id = UUID.randomUUID();
    }
}
