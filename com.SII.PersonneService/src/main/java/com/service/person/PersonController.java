package com.service.person;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
/**
 * A RESTFul controller 
 */
@RestController
@Component
public class PersonController {
	
	@Autowired 
	private PersonRepository repository;
	private static final String ENCODE = "UTF-8";

	@RabbitListener(queues = "#{getPersonQueue.name}")
	public String getPerson(byte[] id) throws InterruptedException, UnsupportedEncodingException {
		return repository.findByPersonID(Integer.parseInt(new String(id,ENCODE))).toString();
	}

	@RabbitListener(queues = "#{getAllPersonQueue.name}")
	public String getAllPerson(byte[] id) throws InterruptedException, UnsupportedEncodingException {
		List<Person> persons = repository.findAll();
		String response = "Il y a actuellement " + persons.size() + " personnes:\n";
		for (Person person : persons){
			response = response + person.getPseudo() + " de type " + person.getJob() + ".\n";
		}
		return response;
	}

	@RabbitListener(queues = "#{addPersonQueue.name}")
	public String addPerson(byte[] data) throws InterruptedException, UnsupportedEncodingException {
		Person p =  (Person) SerializationUtils.deserialize(data);
		repository.save(p);
		return "ok";
	}
}
