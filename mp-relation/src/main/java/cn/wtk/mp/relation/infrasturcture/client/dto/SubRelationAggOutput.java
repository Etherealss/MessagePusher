package cn.wtk.mp.relation.infrasturcture.client.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/15
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubRelationAggOutput {
    List<String> relations;
}
