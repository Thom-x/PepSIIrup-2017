package com.service.suggestion;

import java.time.Duration;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modele.Suggestion;

import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

/**
 * Controller of the Suggestion Service
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */

@RestController
@Component
public class SuggestionController {

	@Autowired
	private SuggestionRepository repository;
	private static final String ENCODE = "UTF-8";
	@Value("${spring.application.name}")
	private String appName;
	
	public SuggestionController(){
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
		.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))	
		.createLogger());
	}
	
	/**
	 * method to save a suggestion in the DB
	 * @param data
	 * @return
	 * @throws JsonProcessingException
	 */
	@RabbitListener(queues = "#{saveSuggestionQueue.name}")
	public String saveEvent(byte[] data){
		String res = "";
		Suggestion s = (Suggestion)SerializationUtils.deserialize(data);

		s = repository.save(s);
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("MemberName", "saveSuggestion")
		.forContext("Service", appName)
		.information("RabbitMQ : saveSuggestion");
		try {
			res =  mapper.writeValueAsString(s);
		} catch (JsonProcessingException e1) {
			Log
			.forContext("MemberName", "saveSuggestion")
			.forContext("Service", appName)
			.error(e1,"JsonProcessingException");
		}
		return res;
	}
}
