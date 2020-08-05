package com.quickshare.quicksharedingdingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//@MapperScan("com.quickshare.quicksharedingdingservice.mapper")
public class QuickshareDingDingServiceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(QuickshareDingDingServiceApplication.class, args);
    }

}
