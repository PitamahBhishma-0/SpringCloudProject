package com.gaurav.springcloudproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringCloudProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProjectApplication.class, args);
    }

}
