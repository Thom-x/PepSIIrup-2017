package fr.sii.atlantique.siistem.client.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sii.atlantique.siistem.client.model.Person;
import fr.sii.atlantique.siistem.client.service.Constants;
import fr.sii.atlantique.siistem.client.service.RabbitClient;
import org.springframework.amqp.utils.SerializationUtils;
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
 * Rest Controller to use Partipant Service
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@RestController
@Component
@CrossOrigin
public class WebParticipantController {

	private static final String ENCODE = "UTF-8";
	private static final String EXCHANGE = "exc.participant";
	@Value("${spring.application.name}")
	private String appName;


	public WebParticipantController(){
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
			.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))
					.createLogger());
	}
	
	/**
	 * Method to get Participant by Event with RabbitMq
	 * @param id
	 * @return
	 */
    @RequestMapping("/getAllParticipantById")
    public String getAllParticipant(@RequestParam(value="id", defaultValue="1") String id){
    	String response = "";
    	try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getAllParticipantById");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getAllParticipantById")
			.forContext("Service", appName)
			.error(e,"UnsupportedEncodingException");
		}
		Log
		.forContext("MemberName", "getAllParticipantById")
		.forContext("Service", appName)
		.forContext("id", id)
		.information("Request : getAllParticipantById");
    	return response;
    }
    
	/**
	 * Method to add a participant to an event with RabbitMq
	 * @param id
	 * @return
	 */
    @RequestMapping(value = "/saveParticipant",method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String saveParticipant(@RequestParam Map<String, String> body){
    	String person = body.get("person");
    	String event = body.get("event");
    	String res = null;
    	
    	String review ="{\"PersonId\":"+person+",\"EventId\":"+event+",\"Rate\": null,\"Text\": null}" ;
		try {
			res =  new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(review.getBytes(ENCODE),"addNewParticipant");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "saveParticipant")
			.forContext("Service", appName)
			.error(e,"UnsupportedEncodingException");
		}
		return res;
    }
    
	/**
	 * Method to add a participant to an event with RabbitMq
	 * @param id
	 * @return
	 */
    @RequestMapping(value = "/cancelParticipation",method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String cancelParticipation(@RequestParam Map<String, String> body){

    	String person = body.get("person");
    	String event = body.get("event");
    	String review ="{\"PersonId\":"+person+",\"EventId\":"+event+",\"Rate\": null,\"Text\": null}" ;
    	String res = null;

		try {
			res = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(review.getBytes(ENCODE),"cancelParticipation");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "registerPerson")
			.forContext("Service", appName)
			.error(e,"UnsupportedEncodingException");
		}
		return res;
    }
    
    
	/**
	 * Method to all Events of a Person with RabbitMq
	 * @param id
	 * @return
	 */
	@RequestMapping("/getEventsByPerson")
	public String getEventsByPerson(@RequestParam(value="id", defaultValue="1") String id){
		String person = null;
		try {
			person = new RabbitClient("exc.person").rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getPersonById");
		} catch (UnsupportedEncodingException e1) {
			Log
			.forContext("MemberName", "getEventsByPerson")
			.forContext("Service", appName)
			.error(e1,"UnsupportedEncodingException");
		}
		ObjectMapper mapper = new ObjectMapper();
		String response = null;
		try {
			response = new RabbitClient("exc.event").rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(mapper.readValue(person, Person.class)),"getEventsByPerson");
		} catch (IOException e) {
			Log
			.forContext("MemberName", "getEventsByPerson")
			.forContext("Service", appName)
			.error(e,"IOException");
		}
		Log
		.forContext("MemberName", "getEventsByPerson")
		.forContext("Service", appName)
		.information("Request : getEventsByPerson");
		return response;
	}
    
    
}
