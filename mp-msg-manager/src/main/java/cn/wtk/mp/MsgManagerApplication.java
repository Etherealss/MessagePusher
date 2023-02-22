package cn.wtk.mp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author wtk
 * @date 2023-02-18
 */
@EnableFeignClients(basePackages = "cn.wtk.mp")
@EnableDiscoveryClient // nacos 服务注册与发现
@SpringBootApplication
@EnableRetry
public class MsgManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsgManagerApplication.class, args);
    }
}