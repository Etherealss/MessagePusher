package cn.wtk.mp.connect.infrastructure.client.dto;

import cn.wtk.mp.common.base.enums.ApiInfo;
import cn.wtk.mp.connect.domain.msg.connector.TransferMsg;
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
    TransferMsg msg;

    public ChannelMsg(ApiInfo apiInfo) {
        this(apiInfo.getCode(), apiInfo.getMessage(), null);
    }
    public ChannelMsg(ApiInfo apiInfo, String desc) {
        this(apiInfo.getCode(), apiInfo.getMessage() + " " + desc, null);
    }

    public ChannelMsg(TransferMsg msg) {
        this(ApiInfo.OK.getCode(), ApiInfo.OK.getMessage(), msg);
    }

    public ChannelMsg(Integer code, String desc, TransferMsg msg) {
        this.code = code;
        this.desc = desc;
        this.msg = msg;
    }
}
