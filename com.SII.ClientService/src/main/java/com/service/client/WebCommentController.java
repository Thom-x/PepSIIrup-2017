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
 * Rest Controller to use Comment Service
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@RestController
@Component
@CrossOrigin
public class WebCommentController {

	private static final String ENCODE = "UTF-8";
	private static final String EXCHANGE = "exc.comment";
	@Value("${spring.application.name}")
	private String appName;

	public WebCommentController(){
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
			.writeTo(new SeqSink(Constants.LOGSERVER_ADDR, Constants.LOGSERVER_SERVICE_APIKEY, null, Duration.ofSeconds(2), null, levelswitch))	
					.createLogger());
	}
	
	/**
	 * Method to get Comment by Event with RabbitMq
	 * @param id
	 * @return
	 */
    @RequestMapping("/getCommentByEvent")
    public String getComment(@RequestParam(value="id", defaultValue="1") String id){
    	String response = "";
    	try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getCommentByEvent");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getCommentByEvent")
			.forContext("Service", appName)
			.error(e,"{date} UnsupportedEncodingException");
		}
		Log
		.forContext("MemberName", "getCommentByEvent")
		.forContext("Service", appName)
		.forContext("id", id)
		.information("Request : getCommentByEvent");
    	return response;
    }
	
}
