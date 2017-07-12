package fr.sii.atlantique.siistem.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sii.atlantique.siistem.client.model.Event;
import fr.sii.atlantique.siistem.client.model.Person;
import fr.sii.atlantique.siistem.client.service.Constants;
import fr.sii.atlantique.siistem.client.service.RabbitClient;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Map;

/**
 * Rest Controller to use Comment Event
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */

@RestController
@Component
@CrossOrigin
public class WebEventController {

	private static final String ENCODE = "UTF-8";
	private static final String EXCHANGE = "exc.event";
	@Value("${spring.application.name}")
	private String appName;


	public WebEventController(){
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
			.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))
					.createLogger());
	}

	/**
	 * Method to all Events of the event owner with RabbitMq
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findByOwner", method = RequestMethod.POST)
	public String getEvent(@RequestBody Person pers){
		String response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(pers),"findByOwner");
		Log
		.forContext("MemberName", "findByOwner")
		.forContext("Service", appName)
		.information("Request : findByOwner");
		return response;
	}


	/**
	 * Method to find an Event by Place with RabbitMq
	 * @param id
	 * @return
	 */
	@RequestMapping("/getEventByPlace")
	public String getAllEvent(@RequestParam(value="id", defaultValue="1") String id){
		String response = "";
		try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getEventByPlace");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getEventByPlace")
			.forContext("Service", appName)
			.error(e,"{date} UnsupportedEncodingException");
		}
		Log
		.forContext("MemberName", "getEventByPlace")
		.forContext("Service", appName)
		.forContext("id", id)
		.information("Request : getEventByPlace");
		return response;
	}

	/**
	 * Method to save an event with RabbitMq
	 * @return
	 */
	@RequestMapping(value = "/saveEvent", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String updateEvent(@RequestParam Map<String, String> body) {
		ObjectMapper mapper = new ObjectMapper();
		Event event = null;
		try {
			event = mapper.readValue(body.get("event"),Event.class);
		} catch (IOException e1) {
			Log
			.forContext("MemberName", "saveEvent")
			.forContext("Service", appName)
			.error(e1," IOException");
		}
		Log
		.forContext("MemberName", "saveEvent")
		.forContext("Service", appName)
		.forContext("event", body.get("event"))
		.information("Request : saveEvent");
		
		new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(event),"saveEvent");
		return "{\"response\":\"success\"}";

	}
		
	/**
	 * Method to find all Events with RabbitMq
	 * @param id
	 * @return
	 */
	@RequestMapping("/getAllEvent")
	public String findAllEvent(@RequestParam(value="id", defaultValue="1") String id){
		String response ="";
		try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE), "getAllEvent");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getAllEvent")
			.forContext("Service", appName)
			.error(e," UnsupportedEncodingException");
		}
		Log
		.forContext("MemberName", "getAllEvent")
		.forContext("Service", appName)
		.information("Request : getAllEvent");
		return response;
	}
	
	/**
	 * Method to find all Events with RabbitMq
	 * @param id
	 * @return
	 */
	@RequestMapping("/getAllEventType")
	public String findAllEventType(@RequestParam(value="id", defaultValue="1") String id){
		String response ="";
		try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE), "getAllEventType");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getAllEventType")
			.forContext("Service", appName)
			.error(e," UnsupportedEncodingException");
		}
		Log
		.forContext("MemberName", "getAllEventType")
		.forContext("Service", appName)
		.information("Request : getAllEventType");
		return response;
	}
	
	/**
	 * Method to search Events by name with RabbitMq
	 * @param name
	 * @return
	 */
	@RequestMapping("/searchEventByName")
	public String searchEventByName(@RequestParam(value="name", defaultValue="foot") String name){
		String response ="";
		try {
			Log
			.forContext("MemberName", "searchEventByName")
			.forContext("Service", appName)
			.forContext("Name", name)
			.information("Request : searchEventByName");
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(name.getBytes(ENCODE), "searchEventByName");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getAllEventType")
			.forContext("Service", appName)
			.error(e," searchEventByName");
		}
		Log
		.forContext("MemberName", "searchEventByName")
		.forContext("Service", appName)
		.information("Request : searchEventByName");
		return response;
	}	
	
	/**
	 * Method to find all Events with RabbitMq
	 * @param id
	 * @return
	 */
	@RequestMapping("/getUpcommingEvents")
	public String getUpcommingEvents(@RequestParam(value="id", defaultValue="10") String id){
		String response ="";
		try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE), "getUpcommingEvents");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getUpcommingEvents")
			.forContext("Service", appName)
			.error(e," UnsupportedEncodingException");
		}
		Log
		.forContext("MemberName", "getUpcommingEvents")
		.forContext("Service", appName)
		.information("Request : getUpcommingEvents");
		return response;
	}
	
}
