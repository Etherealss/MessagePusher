package cn.wtk.mp.relation.infrasturcture.client.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/15
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class SubRelationAggOutput {
    List<String> relations;
}
