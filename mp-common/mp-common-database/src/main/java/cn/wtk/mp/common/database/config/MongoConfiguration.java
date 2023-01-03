package cn.wtk.mp.common.database.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author wtk
 * @date 2023-01-03
 */
@Configuration
@EnableMongoRepositories(basePackages = "cn.wtk.mp")
@EnableMongoAuditing
public class MongoConfiguration {
}
