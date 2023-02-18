package cn.wtk.mp.relation.infrasturcture.client.command.relation.sub;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023/2/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class RemoveSubRelationCommand extends SubRelationCommand {
}
