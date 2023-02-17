package cn.wtk.mp.common.database.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;

/**
 * @author wtk
 * @date 2023-01-03
 */
@Configuration
@EnableMongoRepositories(basePackages = "cn.wtk.mp")
@EnableMongoAuditing
@Slf4j
public class MongoConfiguration {
    @PostConstruct
    public void init() {
        log.info("服务启动");
    }
}
