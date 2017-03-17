package com.service.person;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
/**
 * Controller of the Person Service
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@RestController
@Component
public class PersonController {
	
	@Autowired 
	private PersonRepository repository;
	private static final String ENCODE = "UTF-8";
	private static final Logger LOGGER = Logger.getLogger(PersonController.class.getName());

	/**
	 * Method to get a Person in DataBase, works with RabbitMq
	 * @param id
	 * @return
	 */
	@RabbitListener(queues = "#{getPersonQueue.name}")
	public String getPerson(byte[] id) {
		String response = "";
		try {
			response = repository.findByPersonID(Integer.parseInt(new String(id,ENCODE))).toString();
		} catch (NumberFormatException | UnsupportedEncodingException e) {
			LOGGER.log( Level.SEVERE, "an exception was thrown", e);
		}
		return response;
	}

	/**
	 * Method to get all Persons in DataBase, works with RabbitMq
	 * @param id
	 * @return
	 */
	@RabbitListener(queues = "#{getAllPersonQueue.name}")
	public String getAllPerson(byte[] id){
		String response = "";
		List<Person> persons = repository.findAll();
		ObjectMapper mapper = new ObjectMapper();
	    mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
	    try {
			response =  mapper.writeValueAsString(persons);
		} catch (JsonProcessingException e) {
			LOGGER.log( Level.SEVERE, "an exception was thrown", e);
		}
		return response;
	}

	/**
	 * Method to add a Person in DataBase, works with RabbitMq
	 * @param data
	 * @return
	 */
	@RabbitListener(queues = "#{addPersonQueue.name}")
	public String addPerson(byte[] data){
		Person p =  (Person) SerializationUtils.deserialize(data);
		repository.save(p);
		return "ok";
	}
}
