package cn.wtk.mp.common.msg.dto.mq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author wtk
 * @date 2023/3/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaMsg {
    @NotNull
    @Valid
    ManagerMsgHeader msgHeader;

    @NotNull
    @Valid
    ManagerMsgBody msgBody;
}
