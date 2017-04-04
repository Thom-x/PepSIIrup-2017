package com.service.client;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modele.Person;

import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;


/**
 * Rest Controller to use Person Service
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */

@RestController
@Component
@CrossOrigin
public class WebPersonController {

	private static final String ENCODE = "UTF-8";
	private static final String EXCHANGE = "exc.person";
	@Value("${spring.application.name}")
	private String appName;
	
	public WebPersonController(){
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
		.writeTo(new SeqSink(Constants.LOGSERVER_ADDR, Constants.LOGSERVER_SERVICE_APIKEY, null, Duration.ofSeconds(2), null, levelswitch))	
		.createLogger());
	}

	/**
	 * Method to find a person by id with RabbitMQ
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPerson")
	public String getPerson(@RequestParam(value="id", defaultValue="1") String id){
		String response = "";
		try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getPerson");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getAllPerson")
			.forContext("Service", appName)
			.error(e,"{date} UnsupportedEncodingException");
		}
		Log
		.forContext("id", id)
		.forContext("MemberName", "getPerson")
		.forContext("Service", appName)
		.information("Request : getPerson");
		return response;
	}

	/**
	 * Method to find all persons with RabbitMQ
	 * @param id
	 * @return
	 */
	@RequestMapping("/getAllPerson")
	public String getAllPerson(@RequestParam(value="id", defaultValue="1") String id){
		String response = "";
		try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getAllPerson");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getAllPerson")
			.forContext("Service", appName)
			.error(e,"{date} UnsupportedEncodingException");
		}
		Log
		.forContext("MemberName", "getAllPerson")
		.forContext("Service", appName)
		.information("Request : getAllPerson");
		return response;
	}

	/**
	 * Method to find add a person with RabbitMQ
	 * @param name
	 * @param job
	 * @return
	 */
	@RequestMapping(value = "/addPerson", method = RequestMethod.POST)
	public String addPerson(@RequestBody Person person){
		Log
		.forContext("person", person)
		.forContext("MemberName", "addPerson")
		.forContext("Service", appName)
		.information("Request : addPerson");
		return new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(person),"addPerson");
	}

}
