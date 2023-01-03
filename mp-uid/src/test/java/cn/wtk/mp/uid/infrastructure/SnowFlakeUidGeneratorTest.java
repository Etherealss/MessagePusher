package cn.wtk.mp.uid.infrastructure;

import cn.wtk.mp.uid.infrastructure.generator.SnowFlakeUidGenerator;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "test")
class SnowFlakeUidGeneratorTest {

    public static void main(String[] args) {
        SnowFlakeUidGenerator snowFlakeUidGenerator =
                new SnowFlakeUidGenerator(1,1);
        for (int i = 0; i < 1000; i++) {
            long id = snowFlakeUidGenerator.nextId();
            System.out.println(id);
        }
    }
}