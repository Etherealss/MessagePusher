package cn.wtk.mp.relation.infrasturcture.client.command.relation.sub;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class SubRelationCommand {
    /**
     * 通过url获取
     */
    Long connectorId;

    @NotNull
    Long subrId;

    @NotBlank
    String relationTopic;
}
