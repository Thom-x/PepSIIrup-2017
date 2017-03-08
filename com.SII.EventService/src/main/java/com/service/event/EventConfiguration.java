package com.service.event;

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
@EntityScan("com.service.event")
public class EventConfiguration {
	@Bean
	public DirectExchange direct() {
		return new DirectExchange("eureka.rpc");
	}

	@Bean
	public EventServer server() {
		return new EventServer();
	}

	@Bean
	public Queue eventQueue() {
		return new AnonymousQueue();
	}


	@Bean
	public Binding binding1a(DirectExchange direct, Queue eventQueue) {
		return BindingBuilder.bind(eventQueue).to(direct).with("event");
	}
}