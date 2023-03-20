package cn.wtk.mp.msg.manager.domain.msg;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023-03-20
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManageMsg {
    MsgHeader msgHeader;
    MsgBody msgBody;
}
