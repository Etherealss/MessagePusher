package cn.wtk.mp.relation.domain.relation.sub;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

/**
 * @author wtk
 * @date 2023/2/14
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubRelationItem {
    Long subscriberId;
    List<String> relationTopic;
    Date createTime;
}
