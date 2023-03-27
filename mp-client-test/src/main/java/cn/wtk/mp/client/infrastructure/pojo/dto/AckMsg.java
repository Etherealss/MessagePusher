package cn.wtk.mp.client.infrastructure.pojo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/**
 * @author wtk
 * @date 2023-02-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AckMsg extends ChannelMsgBody{
    Long msgId;
    Long appId;
    Long senderId;
    Date sendTime;
}
