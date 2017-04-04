package com.service.person;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modele.Person;

import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

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
	@Value("${spring.application.name}")
	private String appName;
	
	public PersonController(){
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
		.writeTo(new SeqSink(Constants.LOGSERVER_ADDR, Constants.LOGSERVER_SERVICE_APIKEY, null, Duration.ofSeconds(2), null, levelswitch))	
		.createLogger());
	}

	/**
	 * Method to get a Person in DataBase, works with RabbitMq
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RabbitListener(queues = "#{getPersonQueue.name}")
	public String getPerson(byte[] id) throws JsonProcessingException {
		Person response = null;
		try {
			response = repository.findByPersonID(Integer.parseInt(new String(id,ENCODE)));
		} catch (NumberFormatException | UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getPerson")
			.forContext("Service", appName)
			.error(e,"{date} Exception");
		}
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("id", id)
		.forContext("MemberName", "getPerson")
		.forContext("Service", appName)
		.information("Request : getPerson");
		return mapper.writeValueAsString(response);
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
	    try {
			response =  mapper.writeValueAsString(persons);
		} catch (JsonProcessingException e) {
			Log
			.forContext("MemberName", "getAllPerson")
			.forContext("Service", appName)
			.error(e,"{date} JsonProcessingException");
		}
		Log
		.forContext("MemberName", "getAllPerson")
		.forContext("Service", appName)
		.information("Request : getAllPerson");
		return response;
	}

	/**
	 * Method to add a Person in DataBase, works with RabbitMq
	 * @param data
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RabbitListener(queues = "#{addPersonQueue.name}")
	public String addPerson(byte[] data) throws JsonProcessingException{
		Person p =  (Person) SerializationUtils.deserialize(data);
		repository.save(p);
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("MemberName", "addPerson")
		.forContext("Service", appName)
		.information("Request : addPerson");
		return mapper.writeValueAsString(p);
	}
}
