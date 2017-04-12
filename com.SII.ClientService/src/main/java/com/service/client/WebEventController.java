package com.service.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.time.Duration;
import java.util.Collections;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.modele.Event;
import com.modele.Person;

import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

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
	private static final JacksonFactory jacksonFactory = new JacksonFactory();
	private static final String CLIENT_ID = "929890661942-49n2pcequcmns19fe1omff72tqcips1v.apps.googleusercontent.com";
	private HttpTransport transport = new ApacheHttpTransport();

	public WebEventController(){
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
			.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))	
					.createLogger());
	}

	/**
	 * Method to find an Event by Owner with RabbitMq
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
	 * @param id
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/saveEvent", method = RequestMethod.POST)
	public String updateEvent(@RequestBody Event event, @RequestParam(value="id", defaultValue="1") String idTokenString){
		Log
		.forContext("MemberName", "saveEvent")
		.forContext("Service", appName)
		.forContext("event", event)
		.information("Request : saveEvent");
		
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
				.setAudience(Collections.singletonList(CLIENT_ID))
				.build();
		GoogleIdToken idToken = null;
		try {
			idToken = verifier.verify(idTokenString);
		} catch (GeneralSecurityException | IOException e) {
			Log
			.forContext("MemberName", "getAllPerson")
			.forContext("Service", appName)
			.error(e,"Exception");
		}
		if (idToken != null) {
			Payload payload = idToken.getPayload();
			// Print user identifier
			String userId = payload.getSubject();

			// Get profile information from payload
			String email = payload.getEmail();
			String name = (String) payload.get("name");

			Log
			.forContext("id", idTokenString)
			.forContext("email", email)
			.forContext("userId", userId)
			.forContext("name", name)
			.forContext("Service", appName)
			.information("User Connection");		
			new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(event),"saveEvent");
			return "{\"response\":\"success\"}";
		} else {
			Log
			.forContext("Service", appName)
			.information("Invalid Token");
			return "{\"response\":\"error\"}";
		}		
		
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
			.error(e,"{date} UnsupportedEncodingException");
		}
		Log
		.forContext("MemberName", "getAllEvent")
		.forContext("Service", appName)
		.information("Request : getAllEvent");
		return response;
	}

}
