package fr.sii.atlantique.siistem.client.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.Map;


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
				.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))
				.createLogger());
	}

	/**
	 * Method to find a person by id with RabbitMQ
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPersonById")
	public String getPersonById(@RequestParam(value="id", defaultValue="1") String id){
		String response = "";
		try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getPersonById");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getPersonById")
			.forContext("Service", appName)
			.error(e,"UnsupportedEncodingException");
		}
		Log
		.forContext("id", id)
		.forContext("MemberName", "getPersonById")
		.forContext("Service", appName)
		.information("Request : getPersonById");
		return response;
	}

	/**
	 * Method to find a person by id with RabbitMQ
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPersonByEmail")
	public String getPersonByEmail(@RequestParam(value="email", defaultValue="1") String email){
		String response = "";
		try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(email.getBytes(ENCODE),"getPersonByEmail");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getPersonByEmail")
			.forContext("Service", appName)
			.error(e," UnsupportedEncodingException");
		}
		Log
		.forContext("email", email)
		.forContext("MemberName", "getPersonByEmail")
		.forContext("Service", appName)
		.information("Request : getPersonByEmail");
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
			.error(e,"UnsupportedEncodingException");
		}
		Log
		.forContext("MemberName", "getAllPerson")
		.forContext("Service", appName)
		.information("Request : getAllPerson");
		return response;
	}

	/**
	 * Method to  add a person
	 * @param pers
	 * @param idTokenString
	 * @return
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */

	@RequestMapping(value="/registerPerson", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String registerPerson(@RequestParam Map<String, String> body ){
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("person", body.get("person"))
		.forContext("userId",body.get("tokenid"))
		.forContext("Service", appName)
		.information("User Connection");		
		Person pers = null;
		try {
			pers = mapper.readValue((String) body.get("person"),Person.class);
		} catch (IOException e1) {
			Log
			.forContext("MemberName", "registerPerson")
			.forContext("Service", appName)
			.error(e1,"IOException");
		}
		if (pers != null) {
			Log
			.forContext("FirstName", pers.getFirstName())
			.forContext("LastName", pers.getLastName())
			.forContext("Job", pers.getJob())
			.information("Pers");
			
			return new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(pers),"addPerson");
		} else {
			Log
			.forContext("Service", appName)
			.information("Invalid Token");
			return "{\"response\":\"error\"}";
		}		
	}
	
	/**
	 * Method to find all persons with RabbitMQ
	 * @param id
	 * @return
	 */
	@RequestMapping("/get5Sec")
	public String get5Sec(){
		String response = "Born to be a #Fungenieur";
		try {
		    Thread.sleep(5000);                 
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		Log
		.forContext("MemberName", "getAllPerson")
		.forContext("Service", appName)
		.information("Request : getAllPerson");
		return response;
	}
	
}
