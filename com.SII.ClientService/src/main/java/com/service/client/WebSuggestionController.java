package com.service.client;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.modele.Suggestion;

import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

/**
 * Rest Controller to use Suggestion
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@RestController
@Component
@CrossOrigin
public class WebSuggestionController {

	private static final String ENCODE = "UTF-8";
	private static final String EXCHANGE = "exc.event";
	@Value("${spring.application.name}")
	private String appName;
	private static final JacksonFactory jacksonFactory = new JacksonFactory();
	private static final String CLIENT_ID = "929890661942-49n2pcequcmns19fe1omff72tqcips1v.apps.googleusercontent.com";
	private HttpTransport transport = new ApacheHttpTransport();

	public WebSuggestionController(){
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
			.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))	
					.createLogger());
	}
	
	/**
	 * Method to save an event with RabbitMq
	 * @param id
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/saveSuggestion", method = RequestMethod.POST)
	public String saveSuggestion(@RequestParam Map<String, String> body){
		ObjectMapper mapper = new ObjectMapper();
		Suggestion suggestion = null;
		try {
			suggestion = mapper.readValue((String) body.get("suggestion"),Suggestion.class);
		} catch (IOException e1) {
			Log
			.forContext("MemberName", "saveSuggestion")
			.forContext("Service", appName)
			.error(e1," IOException");
		}
		Log
		.forContext("MemberName", "saveEvent")
		.forContext("Service", appName)
		.forContext("event", body.get("event"))
		.information("Request : saveEvent");
		
		String idTokenString = body.get("tokenid");
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
				.setAudience(Collections.singletonList(CLIENT_ID))
				.build();
		GoogleIdToken idToken = null;
		try {
			idToken = verifier.verify(idTokenString);
		} catch (GeneralSecurityException | IOException e) {
			Log
			.forContext("MemberName", "saveSuggestion")
			.forContext("Service", appName)
			.error(e,"Exception");
		}
		if (idToken != null) {
			Payload payload = idToken.getPayload();
			String userId = payload.getSubject();
			String email = payload.getEmail();
			String name = (String) payload.get("name");

			Log
			.forContext("id", idTokenString)
			.forContext("email", email)
			.forContext("userId", userId)
			.forContext("name", name)
			.forContext("Service", appName)
			.information("User Connection");		
			return new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(suggestion),"saveSuggestion");
		} else {
			Log
			.forContext("Service", appName)
			.information("Invalid Token");
			return "{\"response\":\"error\"}";
		}		
		
	}
}
