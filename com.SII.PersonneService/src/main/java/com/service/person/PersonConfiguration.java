package com.service.person;

import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EntityScan("com.service.person")
public class PersonConfiguration {

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
