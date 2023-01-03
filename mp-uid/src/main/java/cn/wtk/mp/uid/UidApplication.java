package cn.wtk.mp.uid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wtk
 * @date 2023-01-02
 */
@ComponentScan("cn.wtk.mp")
@EnableFeignClients(basePackages = "cn.wtk.mp")
@EnableDiscoveryClient
@SpringBootApplication
public class UidApplication {
    public static void main(String[] args) {
        SpringApplication.run(UidApplication.class, args);
    }
}