package cn.wtk.mp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wtk
 * @date 2022-10-14
 */
@EnableFeignClients(basePackages = "cn.wtk.mp")
@EnableDiscoveryClient // nacos 服务注册与发现
@SpringBootApplication(scanBasePackages = "cn.wtk.mp")
public class ConnectApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConnectApplication.class, args);
    }
}