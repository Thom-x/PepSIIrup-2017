package com.service.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Event Service
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@PropertySource("classpath:application.properties")
@Import(EventConfiguration.class)
public class EventServer {
	
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "event-server");
		
		SpringApplication.run(EventServer.class, args);
		
	}
}
