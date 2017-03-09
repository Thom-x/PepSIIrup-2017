package com.service.client;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfiguration {
	
	@Bean
	public ConnectionFactory connectionFactory() {
	    CachingConnectionFactory connectionFactory =
	        new CachingConnectionFactory("10.10.1.155");
	    return connectionFactory;
	}

	@Bean
	public DirectExchange direct() {
		return new DirectExchange("eureka.rpc");
	}
	
	@Bean
	public WebServer client() {
 	 	return new WebServer();
	}
	
}
