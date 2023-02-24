package cn.wtk.mp.msg.manager.domain.msg;

import cn.wtk.mp.common.msg.entity.Msg;

import java.util.List;

/**
 * @author wtk
 * @date 2023/2/23
 */
public interface IMsgDispatcher<T extends Msg> {
    /**
     * 推送数据
     * @param msg 消息
     * @return 推送失败的 rcvrId
     */
    List<Long> doDispatch(T msg);
}
