package cn.wtk.mp.msg.manager.domain.msg.store.notif;

import cn.wtk.mp.common.msg.entity.DeviceMsg;
import cn.wtk.mp.common.msg.enums.MsgType;
import cn.wtk.mp.msg.manager.domain.msg.store.IMsgService;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.MsgConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wtk
 * @date 2023-02-18
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class NotifMsgService implements IMsgService<DeviceMsg> {

    private static final MsgType SUPPORT = MsgType.DEVICE;

    private final MongoTemplate mongoTemplate;
    private final MsgConverter msgConverter;

    @Override
    public MsgType getSupport() {
        return NotifMsgService.SUPPORT;
    }

    @Override
    public void insert(DeviceMsg msg) {
        NotifMsgEntity entity = msgConverter.toEntity(msg);
        mongoTemplate.insert(entity);
    }
}
