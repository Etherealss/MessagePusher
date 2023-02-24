package cn.wtk.mp.msg.manager.domain.msg.dispatcher;

import cn.wtk.mp.common.msg.entity.GroupMsg;
import cn.wtk.mp.msg.manager.domain.msg.IMsgDispatcher;
import cn.wtk.mp.msg.manager.infrasturcture.client.converter.MsgConverter;
import cn.wtk.mp.msg.manager.infrasturcture.config.AsyncConfiguration;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.command.MsgPushCommand;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.command.MultiMsgPushCommand;
import cn.wtk.mp.msg.manager.infrasturcture.remote.dto.connect.ConnectorAddressDTO;
import cn.wtk.mp.msg.manager.infrasturcture.remote.feign.ConnectFiegn;
import cn.wtk.mp.msg.manager.infrasturcture.remote.feign.RelationFeign;
import cn.wtk.mp.msg.manager.infrasturcture.service.MsgPusher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author wtk
 * @date 2023/2/23
 */
@Slf4j
@Component
public class GroupRcvrMsgDispathcer implements IMsgDispatcher<GroupMsg> {

    private final MsgPusher msgPusher;
    private final ConnectFiegn connectFiegn;
    private final MsgConverter converter;
    private final RelationFeign relationFeign;
    private final ThreadPoolTaskExecutor feignTaskExecutor;

    public GroupRcvrMsgDispathcer(MsgPusher msgPusher,
                                  ConnectFiegn connectFiegn,
                                  MsgConverter converter,
                                  RelationFeign relationFeign,
                                  @Qualifier(AsyncConfiguration.FEIGN)
                                  ThreadPoolTaskExecutor feignTaskExecutor) {
        this.msgPusher = msgPusher;
        this.connectFiegn = connectFiegn;
        this.converter = converter;
        this.relationFeign = relationFeign;
        this.feignTaskExecutor = feignTaskExecutor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Long> doDispatch(GroupMsg msg) {
        Long rcvrId = msg.getRcvrId();
        // 异步执行两个 RPC
        Future<List<Long>> rcvrIdsFuture = feignTaskExecutor.submit(
                () -> relationFeign.getGroupMembers(msg.getGroupId())
        );
        Future<ConnectorAddressDTO> rcvrAddressFuture = feignTaskExecutor.submit(
                () -> connectFiegn.getConnectorAddress(rcvrId)
        );
        MsgPushCommand msgPushCommand = converter.toPushDTO(msg);
        ConnectorAddressDTO rcvrAddress;
        try {
            msgPushCommand.setRcvrIds(rcvrIdsFuture.get());
            rcvrAddress = rcvrAddressFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("异步执行 RPC 错误：" + e.getMessage(), e);
        }
        MultiMsgPushCommand multiMsgPushCommand = new MultiMsgPushCommand(msgPushCommand);
        boolean successful = msgPusher.pushMsg(multiMsgPushCommand, rcvrAddress.getIp(), rcvrAddress.getPort());
        if (successful) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(rcvrId);
        }
    }
}
