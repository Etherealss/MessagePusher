package cn.wtk.mp.relation.infrasturcture.client.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author wang tengkun
 * @date 2023/4/3
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupDTO {
    Long groupId;

    /**
     * 群组归属的 APP ID
     */
    Long appId;

    /**
     * 群组的业务标识符
     */
    String groupTopic;

    /**
     * 群主，创建者
     */
    Long creatorId;

    /**
     * 群成员
     */
    List<Long> memberIds;
}
