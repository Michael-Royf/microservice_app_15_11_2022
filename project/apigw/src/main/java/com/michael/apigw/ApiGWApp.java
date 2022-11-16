package com.michael.apigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ApiGWApp {
    public static void main(String[] args) {
        SpringApplication.run(ApiGWApp.class, args);
    }
}
