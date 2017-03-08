package com.service.client;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {

	@Bean
	public DirectExchange direct() {
		return new DirectExchange("eureka.rpc");
	}
	
	@Bean
	public WebServer client() {
 	 	return new WebServer();
	}
	
}
