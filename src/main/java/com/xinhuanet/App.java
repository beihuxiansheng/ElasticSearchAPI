package com.xinhuanet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"spring-dao.xml", "aliyunMQ-customer.xml"})
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}