package cn.wtk.mp.connect.infrastructure.client.command;

import cn.wtk.mp.connect.infrastructure.enums.MsgType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author wtk
 * @date 2023/2/24
 */
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
@AllArgsConstructor
@NoArgsConstructor
public class MsgPushCommand {
    @NotNull
    Long msgId;
    @NotNull
    Long appId;
    @NotNull
    MsgType msgType;
    @NotBlank
    String msgTopic;
    @NotEmpty
    List<Long> rcvrIds;
    @NotNull
    Object payload;
    @NotNull
    Date sendTime;
    @NotNull
    Date saveTime;
    @NotNull
    Long senderId;

    Long groupId;
}
