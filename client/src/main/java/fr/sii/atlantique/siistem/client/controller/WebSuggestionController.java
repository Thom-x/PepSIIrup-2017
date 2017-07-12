package fr.sii.atlantique.siistem.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import fr.sii.atlantique.siistem.client.model.Suggestion;
import fr.sii.atlantique.siistem.client.service.Constants;
import fr.sii.atlantique.siistem.client.service.OauthTokenVerifier;
import fr.sii.atlantique.siistem.client.service.RabbitClient;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.util.Map;

/**
 * Rest Controller to use Suggestion
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@RestController
@Component
@CrossOrigin
public class WebSuggestionController {

	private static final String EXCHANGE = "exc.suggestion";
	@Value("${spring.application.name}")
	private String appName;

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
		.forContext("MemberName", "saveSuggestion")
		.forContext("Service", appName)
		.forContext("suggestion", body.get("suggestion"))
		.information("Request : saveSuggestion");
		
		GoogleIdToken idToken = OauthTokenVerifier.checkGoogleToken(body.get("tokenid"));
		if (idToken != null) {
			return new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(suggestion),"saveSuggestion");
		} else {
			Log
			.forContext("Service", appName)
			.information("Invalid Token");
			return "{\"response\":\"error\"}";
		}		
		
	}
}
