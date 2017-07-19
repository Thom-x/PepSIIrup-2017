package fr.sii.atlantique.siistem.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class EventServer {
	
	public static void main(String[] args) {
		SpringApplication.run(EventServer.class);
	}

}
