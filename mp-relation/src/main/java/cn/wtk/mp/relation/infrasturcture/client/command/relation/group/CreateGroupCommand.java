package cn.wtk.mp.relation.infrasturcture.client.command.relation.group;

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
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupCommand {

    /**
     * 群组归属的 APP ID
     */
    @NotNull
    Long appId;

    /**
     * 群组的业务标识符
     */
    @NotBlank
    String groupTopic;

    /**
     * 群主，创建者
     */
    @NotNull
    Long creatorId;
}
