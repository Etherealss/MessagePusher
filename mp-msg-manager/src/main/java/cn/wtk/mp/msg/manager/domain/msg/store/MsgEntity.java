package cn.wtk.mp.msg.manager.domain.msg.store;

import cn.wtk.mp.common.msg.enums.MsgTransferStatus;
import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.manager.infrasturcture.constant.MsgEntityFieldName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

/**
 * 需要持久化的消息
 * @author wtk
 * @date 2023-02-11
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(MsgEntityFieldName.TABLE)
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class MsgEntity {
    @MongoId(FieldType.INT64)
    @Field(MsgEntityFieldName.MSG_ID)
    Long msgId;

    @Field(MsgEntityFieldName.MSG_ID)
    Long appId;

    @Field(MsgEntityFieldName.MSG_TOPIC)
    String msgTopic;

    @Field(MsgEntityFieldName.DATA)
    Object data;

    @Field(MsgEntityFieldName.TRANSFET_STATUS)
    MsgTransferStatus transferStatus;

    @Field(MsgEntityFieldName.SEND_TIME)
    Date sendTime;

    @Field(MsgEntityFieldName.SAVE_TIME)
    Date saveTime;

    @Field(MsgEntityFieldName.RECV_TIME)
    Date recvTime;

    public abstract MsgType getMsgType();
}
