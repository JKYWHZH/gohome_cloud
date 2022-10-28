package com.jkywhzh.discern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscernApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscernApplication.class, args);
    }
}
