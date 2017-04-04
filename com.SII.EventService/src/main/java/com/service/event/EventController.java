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
		.writeTo(new SeqSink(Constants.LOGSERVER_ADDR, Constants.LOGSERVER_SERVICE_APIKEY, null, Duration.ofSeconds(2), null, levelswitch))	
		.createLogger());
	}

	/**
	 * method to save an event in the DB
	 * @param data
	 * @return
	 * @throws JsonProcessingException
	 */
	@RabbitListener(queues = "#{saveEventQueue.name}")
	public String saveEvent(byte[] data) throws JsonProcessingException{
		Event e = (Event)SerializationUtils.deserialize(data);
		Event event = repository.save(e);
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("MemberName", "saveEvent")
		.forContext("Service", appName)
		.information("RabbitMQ : saveEvent");
		return mapper.writeValueAsString(event);
	}

	/**
	 * method to find an event by his owner
	 * @param owner
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RabbitListener(queues = "#{findByOwnerQueue.name}")
	public String findByOwner(byte[] owner) throws UnsupportedEncodingException{
		Log
		.forContext("MemberName", "findByOwner")
		.forContext("Service", appName)
		.information("RabbitMQ : findByOwner");
		return repository.findByOwner(Integer.parseInt(new String(owner,ENCODE))).toString();
	}

	/**
	 * method to get an event by his place
	 * @param place
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RabbitListener(queues = "#{getEventByPlaceQueue.name}")
	public String getEventByPlace(byte[] place) throws UnsupportedEncodingException{
		Log
		.forContext("MemberName", "getEventByPlace")
		.forContext("Service", appName)
		.information("RabbitMQ : getEventByPlace");
		return repository.getEventFromPlace(new String(place, ENCODE), new Date()).toString();
	}
	
	/**
	 * method to get all the events
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@RabbitListener(queues = "#{getAllEventQueue.name}")
	public String getAllEvent(byte[] id) throws JsonProcessingException{
		List<Event> events = repository.findAll();
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("MemberName", "getAllEvent")
		.forContext("Service", appName)
		.information("RabbitMQ : getAllEvent");
		return mapper.writeValueAsString(events);
	}
}
