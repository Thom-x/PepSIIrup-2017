package fr.sii.atlantique.siistem.personne.service;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sii.atlantique.siistem.personne.model.Person;

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
		.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))	
		.createLogger());
	}

	/**
	 * Method to get a Person by id in DataBase, works with RabbitMq
	 * @param id
	 * @return
	 * @throws JsonProcessingException 
	 */
	@RabbitListener(queues = "#{getPersonQueueById.name}")
	public String getPersonById(byte[] id){
		Person response = null;
		String res = null;
		try {
			response = repository.findByPersonID(Integer.parseInt(new String(id,ENCODE)));
		} catch (NumberFormatException | UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getPersonById")
			.forContext("Service", appName)
			.error(e,"Exception");
		}
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("id", id)
		.forContext("MemberName", "getPersonById")
		.forContext("Service", appName)
		.information("Request : getPersonById");
		try {
			res = mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			Log
			.forContext("MemberName", "getPersonById")
			.forContext("Service", appName)
			.error(e,"JsonProcessingException");
		}
		return res;
	}
	
	/**
	 * Method to get a Person by email in DataBase, works with RabbitMq
	 * @param email
	 * @return
	 * @throws JsonProcessingException
	 */
	@RabbitListener(queues = "#{getPersonQueueByEmail.name}")
	public String getPersonByEmail(byte[] email) {
		Person response = null;
		String res = null;
		try {
			response = repository.findByPersonEmail(new String(email,ENCODE));
		} catch (NumberFormatException | UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getPersonByEmail")
			.forContext("Service", appName)
			.error(e," Exception");
		}
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("email", email)
		.forContext("MemberName", "getPersonByEmail")
		.forContext("Service", appName)
		.information("Request : getPersonByEmail");
		try {
			res = mapper.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			Log
			.forContext("MemberName", "getPersonByEmail")
			.forContext("Service", appName)
			.error(e,"JsonProcessingException");
		}
		return res;
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
			.error(e," JsonProcessingException");
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
		if (p.checkPerson()){
			p = repository.save(p);
		}
		else{
			Log
			.forContext("MemberName", "addPerson")
			.forContext("Service", appName)
			.error(new IllegalArgumentException(),"IllegalArgumentException");
		}
		String res = "";
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("MemberName", "addPerson")
		.forContext("Service", appName)
		.information("Request : addPerson");
		try {
			res = mapper.writeValueAsString(p);
		} catch (JsonProcessingException e) {
			Log
			.forContext("MemberName", "addPerson")
			.forContext("Service", appName)
			.error(e,"JsonProcessingException");
		}
		return res;
	}
}
