package com.eventService.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

import com.eventService.event.EventConfiguration;

@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(EventConfiguration.class)
public class EventServer {

	/**
	 * Run the application using Spring Boot and an embedded servlet engine.
	 * 
	 * @param args
	 *            Program arguments - ignored.
	 */
	public static void main(String[] args) {
		// Tell server to look for accounts-server.properties or
		// accounts-server.yml
		System.setProperty("spring.config.name", "event-server");

		SpringApplication.run(EventServer.class, args);
	}
}
