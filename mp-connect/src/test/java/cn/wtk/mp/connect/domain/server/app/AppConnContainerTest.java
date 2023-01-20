package cn.wtk.mp.connect.domain.server.app;

import cn.wtk.mp.common.base.utils.UUIDUtil;
import cn.wtk.mp.connect.domain.server.app.connector.Connector;
import cn.wtk.mp.connect.domain.server.app.connector.ConnectorKey;
import cn.wtk.mp.connect.domain.server.app.connector.connection.Connection;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
class AppConnContainerTest {
    public static void main(String[] args) {
        log.info("123123123!!@#!@#!#!@");
        AppConnContainer container = new AppConnContainer(1L);
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
        for (int i = 0; i < size; i++) {
            threadExecutor.submit(new MyRunnable(container, barrier, i));
        }
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
        System.out.println("最终connector数：" + container.getConnectorSize());
        Connector connector = container.getConnector(new ConnectorKey(1L, 1L));
        System.out.println("最终连接数：" + connector.getConnSize());
    }
}

@AllArgsConstructor
@Slf4j
class MyRunnable implements Runnable {

    private final AppConnContainer container;
    private final CyclicBarrier barrier;
    private final int no;

    @Override
    public void run() {
        Connection connection = new Connection(UUIDUtil.get(), 1L, 1L, null);
        container.addConn(connection);
        System.out.println(no + "\t完成");
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            log.error("异常:{}", e.getMessage());
        }
    }
}