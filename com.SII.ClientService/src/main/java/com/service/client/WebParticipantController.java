package com.service.client;

import java.io.UnsupportedEncodingException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

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
}
