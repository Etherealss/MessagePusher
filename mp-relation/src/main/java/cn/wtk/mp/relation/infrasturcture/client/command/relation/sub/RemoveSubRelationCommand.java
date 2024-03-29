package cn.wtk.mp.relation.infrasturcture.client.command.relation.sub;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023/2/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class RemoveSubRelationCommand extends SubRelationCommand {
    public RemoveSubRelationCommand(Long connectorId, @NotNull Long subrId, @NotBlank String relationTopic) {
        super(connectorId, subrId, relationTopic);
    }
}
