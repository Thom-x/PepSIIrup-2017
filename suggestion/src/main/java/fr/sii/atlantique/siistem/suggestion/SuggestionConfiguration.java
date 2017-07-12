package fr.sii.atlantique.siistem.suggestion;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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
public class SuggestionConfiguration {

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
		return new DirectExchange("exc.suggestion");
	}
	
	@Bean
	public Queue saveSuggestionQueue() {
		return new AnonymousQueue();
	}
	
	@Bean
	public Binding binding1(DirectExchange direct, Queue saveSuggestionQueue) {
		return BindingBuilder.bind(saveSuggestionQueue).to(direct).with("saveSuggestion");
	}
	
}
