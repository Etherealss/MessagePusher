package cn.wtk.mp.relation.application.command;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RemoveSubRelationCommand {
    Long connectorId;
    Long subscriberId;
    String relationTopic;
}
