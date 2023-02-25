package cn.wtk.mp.connect.domain.server;

import cn.wtk.mp.common.base.uid.UidGenerator;
import cn.wtk.mp.connect.domain.server.connector.Connector;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j(topic = "test")
@DisplayName("ServerConnContainerTest测试")
@SpringBootTest
class ServerConnContainerTest {
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private UidGenerator uidGenerator;

    @Test
    void test() {
        ServerConnContainer container = new ServerConnContainer(eventPublisher);
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
        BlockingQueue<Long> queue = new LinkedBlockingQueue<>();
        for (int i = 0; i < size; i++) {
            MyRunnable1 task = new MyRunnable1(container, barrier, i, queue, uidGenerator);
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
        for (Long connId : queue) {
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