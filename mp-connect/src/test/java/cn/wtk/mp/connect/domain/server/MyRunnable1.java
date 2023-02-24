package cn.wtk.mp.connect.domain.server;

import cn.wtk.mp.common.base.utils.UUIDUtil;
import cn.wtk.mp.connect.domain.server.connector.connection.Connection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 */
@AllArgsConstructor
@Slf4j
public class MyRunnable1 implements Runnable {

    private final ServerConnContainer container;
    private final CyclicBarrier barrier;
    private final int no;
    private final BlockingQueue<UUID> queue;

    @Override
    public void run() {
        UUID connId = UUIDUtil.get();
        queue.offer(connId);
        Connection connection = new Connection(connId, 1L, 1L, null);
        container.addConn(connection);
        System.out.println(no + "\t完成");
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            log.error("异常:{}", e.getMessage());
        }
    }
}