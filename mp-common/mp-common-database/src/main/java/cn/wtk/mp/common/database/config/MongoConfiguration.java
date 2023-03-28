package cn.wtk.mp.common.database.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author wtk
 * @date 2023-01-03
 */
@Configuration
@EnableMongoRepositories(basePackages = "cn.wtk.mp")
@EnableTransactionManagement
@EnableMongoAuditing
@Slf4j
public class MongoConfiguration  {

    @PostConstruct
    public void init() {
        log.info("MongoConfiguration init");
    }

    /**
     * 注册 自定义的mongo转换器
     *
     * @return 返回注册成功的 MongoCustomConversions
     */
    @Bean
    public MongoCustomConversions customConversions(List<MongoConverter<?, ?>> converters) {
        log.info("MongoDB 注册自定义类型转换器");
        return MongoCustomConversions.create(adapter -> adapter.registerConverters(converters));
    }
}