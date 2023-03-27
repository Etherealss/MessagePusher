package cn.wtk.mp.client.infrastructure.pojo.dto;

import cn.wtk.mp.common.base.enums.ApiInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023-03-21
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class ChannelMsg {
    Integer code;
    String desc;
    ChannelMsgBody msg;

    public ChannelMsg(ApiInfo apiInfo) {
        this(apiInfo.getCode(), apiInfo.getMessage(), null);
    }
    public ChannelMsg(ApiInfo apiInfo, String desc) {
        this(apiInfo.getCode(), apiInfo.getMessage() + desc, null);
    }

    public ChannelMsg(ChannelMsgBody msg) {
        this(ApiInfo.OK.getCode(), ApiInfo.OK.getMessage(), msg);
    }

    public ChannelMsg(Integer code, String desc, ChannelMsgBody msg) {
        this.code = code;
        this.desc = desc;
        this.msg = msg;
    }
}
