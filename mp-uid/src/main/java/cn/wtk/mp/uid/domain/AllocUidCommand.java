package cn.wtk.mp.uid.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;

/**
 * @author wtk
 * @date 2023-01-02
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AllocUidCommand {
    @Min(1)
    private Integer count;
}
