package cn.wtk.mp.common.database.pojo.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wtk
 * @date 2022-02-21
 */
@Data
public abstract class BaseEntity implements Serializable {
    /**
     * 创建时间
     */
    @CreatedDate
    protected Date createTime;
    /**
     * 修改时间
     */
    @LastModifiedDate
    protected Date updateTime;
}
