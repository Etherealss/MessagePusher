package cn.wtk.mp.msg.manager.domain.msg.store;

import cn.wtk.mp.common.msg.entity.Msg;
import cn.wtk.mp.common.msg.enums.MsgType;

/**
 * @author wtk
 * @date 2023/2/23
 */
public interface IMsgService<T extends Msg> {
    MsgType getSupport();
    void insert(T msg);
}
