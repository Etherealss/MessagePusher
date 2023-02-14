package cn.wtk.mp.relation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wtk
 * @date 2023/2/14
 */
@EnableFeignClients(basePackages = "cn.wtk.mp")
@EnableDiscoveryClient // nacos 服务注册与发现
@SpringBootApplication
public class RelationApplication {
    public static void main(String[] args) {
        SpringApplication.run(RelationApplication.class, args);
    }
}