package cn.wtk.mp.msg.manager.domain.msg;

import cn.wtk.mp.common.msg.entity.Msg;
import cn.wtk.mp.common.msg.enums.MsgType;

/**
 * @author wtk
 * @date 2023/2/23
 */
public interface IMsgDispatcher<T extends Msg> {
    void doDispatch(T msg);
}
