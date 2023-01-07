package cn.wtk.mp.common.base.pojo;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author wtk
 */
@Getter
public abstract class DomainEvent extends ApplicationEvent {
    private final UUID id;
    private final LocalDateTime occurredOn;

    public DomainEvent(Object source, Integer userId) {
        super(source);
        occurredOn = LocalDateTime.now();
        id = UUID.randomUUID();
    }
}
