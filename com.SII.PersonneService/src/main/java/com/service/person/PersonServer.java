package com.service.person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@EnableAutoConfiguration
@EnableDiscoveryClient
@PropertySource("classpath:application.properties")
@Import(PersonConfiguration.class)
public class PersonServer {
	
    public static void main(String[] args) {
        // Will configure using accounts-server.yml
        System.setProperty("spring.config.name", "person-server");

        SpringApplication.run(PersonServer.class, args);
    }
}