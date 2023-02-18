package cn.wtk.mp.relation.infrasturcture.client.command.relation.group;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023/2/14
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class QuitGroupRelationCommand {
    /**
     * 通过url获取
     */
    Long groupId;

    /**
     * 要退出的群成员ID
     */
    @NotNull
    Long quitterId;
}
