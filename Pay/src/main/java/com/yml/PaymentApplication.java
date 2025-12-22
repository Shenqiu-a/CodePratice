package com.yml;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0915:19
 */

@SpringBootApplication
@Slf4j
@EnableDubbo
public class PaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class,args);
        log.info("*****************    支付微服务启动成功    *****************");
    }
}
