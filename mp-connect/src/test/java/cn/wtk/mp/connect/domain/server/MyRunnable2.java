package cn.wtk.mp.connect.domain.server;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@AllArgsConstructor
@Slf4j
public class MyRunnable2 implements Runnable {

    private final ServerConnContainer container;
    private final CyclicBarrier barrier;
    private final Long connectorId;
    private final Long connId;

    @Override
    public void run() {
        container.removeConn(connectorId, connId);
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            log.error("异常:{}", e.getMessage());
        }
    }
}