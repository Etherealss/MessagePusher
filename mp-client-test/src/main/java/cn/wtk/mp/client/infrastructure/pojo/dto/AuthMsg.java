package cn.wtk.mp.client.infrastructure.pojo.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023-02-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthMsg extends ChannelMsgBody{
    String authUrl;
}
