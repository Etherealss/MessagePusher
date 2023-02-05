package cn.wtk.mp.connect.domain.server.app;

import cn.wtk.mp.common.base.utils.UUIDUtil;
import cn.wtk.mp.connect.domain.server.ServerConnContainer;
import cn.wtk.mp.connect.domain.server.connector.Connector;
import cn.wtk.mp.connect.domain.server.connector.connection.Connection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
class AppConnContainerTest {
    public static void main(String[] args) {
        ServerConnContainer container = new ServerConnContainer();
        int size = 100;
        CyclicBarrier barrier = new CyclicBarrier(size);
        ThreadPoolExecutor threadExecutor = new ThreadPoolExecutor(
                size, size, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(size), new ThreadFactory() {
            private AtomicInteger seq = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                int i = seq.addAndGet(1);
                return new Thread(r, String.valueOf(i));
            }
        }
        );
        BlockingQueue<UUID> queue = new LinkedBlockingQueue<>();
        for (int i = 0; i < size; i++) {
            MyRunnable1 task = new MyRunnable1(container, barrier, i, queue);
            threadExecutor.submit(task);
        }
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        System.out.println("最终connector数：" + container.getConnetorSize());
        long connectorId = 1L;
        Connector connector = container.getConnector(connectorId);
        System.out.println("最终连接数：" + connector.getConnSize());

        // remove 测试
        barrier.reset();
        for (UUID connId : queue) {
            MyRunnable2 task = new MyRunnable2(container, barrier, connectorId, connId);
            threadExecutor.submit(task);
        }
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        System.out.println("最终连接数：" + connector.getConnSize());
        threadExecutor.shutdownNow();
    }
}

@AllArgsConstructor
@Slf4j
class MyRunnable1 implements Runnable {

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

@AllArgsConstructor
@Slf4j
class MyRunnable2 implements Runnable {

    private final ServerConnContainer container;
    private final CyclicBarrier barrier;
    private final Long connectorId;
    private final UUID connId;

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