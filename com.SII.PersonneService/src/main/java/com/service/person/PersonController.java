package com.service.person;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
/**
 * A RESTFul controller 
 */
@RestController
@Component
public class PersonController {
	
	@Autowired 
	private PersonRepository repository;
	


	@RabbitListener(queues = "#{personQueue.name}")
	 public String getAllPerson(int id) throws InterruptedException {
		System.out.println(id);
		List<Person> persons = repository.findAll();
    	String response = "Il y a actuellement " + persons.size() + " personnes:\n";
    	for (Person person : persons){
    		response = response + person.getPseudo() + " de type " + person.getJob() + ".\n";
    	}
    	System.out.println(response);
    	return response;
	}
    
}
