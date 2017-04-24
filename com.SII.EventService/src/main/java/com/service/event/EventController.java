package com.service.event;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modele.Event;
import com.modele.Person;

import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

/**
 * Controller of the Event Service
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */

@RestController
@Component
public class EventController {

	@Autowired
	private EventRepository repository;
	private static final String ENCODE = "UTF-8";
	@Value("${spring.application.name}")
	private String appName;
	
	public EventController(){
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
		.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))	
		.createLogger());
	}

	/**
	 * method to save an event in the DB
	 * @param data
	 * @return
	 * @throws JsonProcessingException
	 */
	@RabbitListener(queues = "#{saveEventQueue.name}")
	public String saveEvent(byte[] data){
		String res = null;
		Event e = (Event)SerializationUtils.deserialize(data);
		Event event = null;
		if (e.checkEvent()){
			 event = repository.save(e);
		}
		else{
			Log
			.forContext("MemberName", "saveEvent")
			.forContext("Service", appName)
			.error(new IllegalArgumentException(),"IllegalArgumentException");
		}
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("MemberName", "saveEvent")
		.forContext("Service", appName)
		.information("RabbitMQ : saveEvent");
		try {
			res =  mapper.writeValueAsString(event);
		} catch (JsonProcessingException e1) {
			Log
			.forContext("MemberName", "saveEvent")
			.forContext("Service", appName)
			.error(e1,"JsonProcessingException");
		}
		return res;
	}

	/**
	 * method to find all events by  owner
	 * @param owner
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RabbitListener(queues = "#{findByOwnerQueue.name}")
	public String findByOwner(byte[] owner){
		
		Log
		.forContext("MemberName", "findByOwner")
		.forContext("Service", appName)
		.information("RabbitMQ : findByOwner");
		return repository.findByOwner((Person)SerializationUtils.deserialize(owner)).toString();
	}
	
	/**
	 * method to find all event of a person
	 * @param owner
	 * @return
	 * @throws JsonProcessingException 
	 * @throws UnsupportedEncodingException
	 */
	@RabbitListener(queues = "#{getEventsByPersonQueue.name}")
	public String getEventsByPerson(byte[] person) throws JsonProcessingException{
		List<Event> res;
		Log
		.forContext("MemberName", "getEventsByPerson")
		.forContext("Service", appName)
		.information("RabbitMQ : getEventsByPerson");
		res = repository.getEventsByPerson((Person)SerializationUtils.deserialize(person));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(res);
	}
	

	/**
	 * method to get an event by his place
	 * @param place
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RabbitListener(queues = "#{getEventByPlaceQueue.name}")
	public String getEventByPlace(byte[] place){
		String res = "";
		Log
		.forContext("MemberName", "getEventByPlace")
		.forContext("Service", appName)
		.information("RabbitMQ : getEventByPlace");
		try {
			res = repository.getEventFromPlace(new String(place, ENCODE), new Date()).toString();
		} catch (UnsupportedEncodingException e1) {
			Log
			.forContext("MemberName", "getEventByPlace")
			.forContext("Service", appName)
			.error(e1,"UnsupportedEncodingException");
		}
		return res;
	}
	
	/**
	 * method to get all the events
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@RabbitListener(queues = "#{getAllEventQueue.name}")
	public String getAllEvent(byte[] id){
		String res = "";
		List<Event> events = repository.findAll();
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("MemberName", "getAllEvent")
		.forContext("Service", appName)
		.information("RabbitMQ : getAllEvent");
		try {
			res = mapper.writeValueAsString(events);
		} catch (JsonProcessingException e1) {
			Log
			.forContext("MemberName", "getAllEvent")
			.forContext("Service", appName)
			.error(e1,"JsonProcessingException");
		}
		return res;
	}
}
