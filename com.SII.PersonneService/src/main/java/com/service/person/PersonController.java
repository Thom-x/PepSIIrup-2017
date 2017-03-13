package com.service.person;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
/**
 * A RESTFul controller 
 */
@RestController
@Component
public class PersonController {
	@Autowired 
	private PersonRepository repository;
	@RabbitListener(queues = "#{personQueue.name}")
	 public String getAllPerson(byte[] id) throws InterruptedException, UnsupportedEncodingException {
		System.out.println(new String(id,"UTF-8"));
		List<Person> persons = repository.findAll();
    	String response = "Il y a actuellement " + persons.size() + " personnes:\n";
    	for (Person person : persons){
    		response = response + person.getPseudo() + " de type " + person.getJob() + ".\n";
    	}
    	return response;
    }
}
