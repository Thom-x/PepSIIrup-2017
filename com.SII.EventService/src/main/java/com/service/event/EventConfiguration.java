package com.service.event;

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

/**
 * Configuration of the Event Service
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@Configuration
@ComponentScan
@EntityScan({"com.service.event", "com.modele"})
public class EventConfiguration {
	
	@Bean
	public ConnectionFactory connectionFactory() {	
		
	    CachingConnectionFactory connectionFactory =
	        new CachingConnectionFactory(Constants.getINSTANCE().getRabbitmqserverAddr());
    		connectionFactory.setUsername(Constants.getINSTANCE().getRabbitmqUsername());
    		connectionFactory.setPassword(Constants.getINSTANCE().getRabbitmqPassword());
	    return connectionFactory;
	}
		
	
	@Bean
	public DirectExchange direct() {
		return new DirectExchange("exc.event");
	}

	@Bean
	public EventServer server() {
		return new EventServer();
	}
	
	@Bean
	public Queue saveEventQueue() {
		return new AnonymousQueue();
	}
	
	@Bean
	public Queue findByOwnerQueue() {
		return new AnonymousQueue();
	}
	
	@Bean
	public Queue getEventByPlaceQueue() {
		return new AnonymousQueue();
	}
	
	@Bean
	public Queue getAllEventQueue() {
		return new AnonymousQueue();
	}
	
	@Bean
	public Binding binding1(DirectExchange direct, Queue saveEventQueue) {
		return BindingBuilder.bind(saveEventQueue).to(direct).with("saveEvent");
	}
	
	@Bean
	public Binding binding2(DirectExchange direct, Queue findByOwnerQueue) {
		return BindingBuilder.bind(findByOwnerQueue).to(direct).with("findByOwner");
	}
	
	@Bean
	public Binding binding3(DirectExchange direct, Queue getEventByPlaceQueue) {
		return BindingBuilder.bind(getEventByPlaceQueue).to(direct).with("getEventByPlace");
	}
	
	@Bean
	public Binding binding4(DirectExchange direct, Queue getAllEventQueue) {
		return BindingBuilder.bind(getAllEventQueue).to(direct).with("getAllEvent");
	}

}