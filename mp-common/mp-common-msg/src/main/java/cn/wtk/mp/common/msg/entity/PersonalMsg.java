package cn.wtk.mp.common.msg.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author wtk
 * @date 2023-02-12
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonalMsg extends PersistentMsg {
    Long senderId;
    Long rcvrId;
    String relationTopic;

    @Override
    public Long getRcvrId() {
        return rcvrId;
    }

    @Override
    public String toString() {
        return "PersonalMsg{" +
                "senderId=" + senderId +
                ", rcvrId=" + rcvrId +
                ", relationTopic='" + relationTopic + '\'' +
                ", transferStatus=" + transferStatus +
                ", saveTime=" + saveTime +
                ", recvTime=" + recvTime +
                ", msgTopic='" + msgTopic + '\'' +
                ", data=" + data +
                ", msgId=" + msgId +
                ", appId=" + appId +
                ", msgType=" + msgType +
                ", sendTime=" + sendTime +
                '}';
    }
}
