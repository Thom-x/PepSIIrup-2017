package com.service.person;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EntityScan("com.service.person")
public class PersonConfiguration {
	
	@Bean
	public ConnectionFactory connectionFactory() {
	    CachingConnectionFactory connectionFactory =
	        new CachingConnectionFactory("10.10.1.155");
	    	connectionFactory.setUsername("guest");
	    	connectionFactory.setPassword("guest");
	    return connectionFactory;
	}
		

	@Bean
	public DirectExchange direct() {
		return new DirectExchange("eureka.rpc");
	}

	@Bean
	public PersonServer server() {
		return new PersonServer();
	}

	@Bean
	public Queue personQueue() {
		return new AnonymousQueue();
	}


	@Bean
	public Binding binding1a(DirectExchange direct, Queue personQueue) {
		return BindingBuilder.bind(personQueue).to(direct).with("person");
	}
}
