package com.eventService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableDiscoveryClient
//@EnableJpaRepositories("com.project.data.spring_jpa.reposito‌​ries")
@Import(EventConfiguration.class)
public class EventServer {

	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 * 
	 * @param args
	 *            Program arguments - ignored.
	 */
	public static void main(String[] args) {
		// Tell server to look for event-server.properties or
		// event-server.yml
		System.setProperty("spring.config.name", "event-server");
		
		SpringApplication.run(EventServer.class, args);
		
	}
}
