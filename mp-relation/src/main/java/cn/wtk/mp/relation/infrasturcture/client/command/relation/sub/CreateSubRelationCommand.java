package cn.wtk.mp.relation.infrasturcture.client.command.relation.sub;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubRelationCommand {
    Long connectorId;
    Long subrId;
    String relationTopic;
}
