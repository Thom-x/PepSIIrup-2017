package com.service.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
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
	private static final JacksonFactory jacksonFactory = new JacksonFactory();
	private static final String CLIENT_ID1 = "1059176547192-jq81i94a7dccnpklm5ph4gauim29t0dg.apps.googleusercontent.com"; //ms	
	private static final String CLIENT_ID2 = "784894623300-gmkq3hut99f16n220kjimotv0os7vt2e.apps.googleusercontent.com"; //java
	private HttpTransport transport = new ApacheHttpTransport();


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
			.error(e,"{date} UnsupportedEncodingException");
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
			.error(e,"{date} UnsupportedEncodingException");
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

	@RequestMapping(value="/connect", method = RequestMethod.POST)
	public String connect(@RequestParam Map<String, String> body ){
		Log
		.forContext("userId",body.get("tokenid"))
		.forContext("Service", appName)
		.information("User Connection");		
		GoogleIdToken idToken = checkGoogleToken(body.get("tokenid"));
		if (idToken != null) {
			Payload payload = idToken.getPayload();
			// Print user identifier
			String userId = payload.getSubject();
			// Get profile information from payload
			String email = payload.getEmail();
			String name = (String) payload.get("name");
			Log
			.forContext("email", email)
			.forContext("name", name)
			.forContext("userId", userId)
			.forContext("Service", appName)
			.information("User Connection");

			String p = null;
			try {
				p = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(email.getBytes("UTF-8"),"getPersonByEmail");
			} catch (UnsupportedEncodingException e) {
				Log
				.forContext("MemberName", "getAllPerson")
				.forContext("Service", appName)
				.error(e,"UnsupportedEncodingException");
			}
			if(p != null){
				return p;
			}
			else{
				return "{\"response\":\"inscription\"}";
			}			
		} else {
			Log
			.forContext("Service", appName)
			.forContext("Token",body.get("tokenid"))
			.information("Invalid Token");
			return "{\"response\":\"error\"}";
		}

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
		GoogleIdToken idToken = checkGoogleToken(body.get("tokenid"));
		if (idToken != null && pers != null) {
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
	 * Method to validate a google id token with Oauth
	 * @param token
	 * @return
	 */
	public GoogleIdToken checkGoogleToken(String token){
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
				.setAudience(Arrays.asList(CLIENT_ID1, CLIENT_ID2))
				.build();
		GoogleIdToken idToken = null;
		try {
			idToken = verifier.verify(token);
		} catch (Exception e) {
			Log
			.forContext("MemberName", "registerPerson")
			.forContext("Service", appName)
			.error(e,"Exception");
		}
		return idToken;
	}
	
}
