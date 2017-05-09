package com.service.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.modele.Comment;

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
			.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))	
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
    
	/**
	 * Method to get response of a comment by comment id  with RabbitMq
	 * @param id
	 * @return
	 */
    @RequestMapping("/getResponseList")
    public String getResponseList(@RequestParam(value="id", defaultValue="1") String id){
		Log
		.forContext("MemberName", "getResponseList")
		.forContext("Service", appName)
		.forContext("id", id)
		.information("Request : getResponseList");
    	String response = "";
    	try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getResponseList");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getResponseList")
			.forContext("Service", appName)
			.error(e,"{date} UnsupportedEncodingException");
		}
		Log
		.forContext("MemberName", "getResponseList")
		.forContext("Service", appName)
		.forContext("id", id)
		.information("Request : getResponseList");
    	return response;
    }
    
	/**
	 * Method to save an comment with RabbitMq
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/saveComment",method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String saveComment(@RequestParam Map<String, String> body){
		Log
		.forContext("MemberName", "saveComment")
		.forContext("Service", appName)
		.forContext("comment", body.get("comment"))
		.information("Request : saveEvent");
		
		ObjectMapper mapper = new ObjectMapper();
		Comment comment = null;
		try {
			comment = mapper.readValue(body.get("comment"),Comment.class);
		} catch (IOException e1) {
			Log
			.forContext("MemberName", "saveComment")
			.forContext("Service", appName)
			.error(e1," IOException");
		}
		Log
		.forContext("MemberName", "saveComment")
		.forContext("Service", appName)
		.forContext("event", body.get("comment"))
		.information("Request : saveComment");
		
		GoogleIdToken idToken = OauthTokenVerifier.checkGoogleToken(body.get("tokenid"));
		if (idToken != null) {
			Payload payload = idToken.getPayload();
			String userId = payload.getSubject();
			String email = payload.getEmail();
			String name = (String) payload.get("name");

			Log
			.forContext("id", body.get("tokenid"))
			.forContext("email", email)
			.forContext("userId", userId)
			.forContext("name", name)
			.forContext("Service", appName)
			.information("User Connection");		
			new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(comment),"postComment");
			return "{\"response\":\"success\"}";
		} else {
			Log
			.forContext("Service", appName)
			.information("Invalid Token");
			return "{\"response\":\"error\"}";
		}		
		
	}
	
	/**
	 * Method to save an comment with RabbitMq
	 * @param id
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/saveCommentG",method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String saveCommentG(@RequestParam Map<String, String> body) throws UnsupportedEncodingException{
		Log
		.forContext("MemberName", "saveComment")
		.forContext("Service", appName)
		.forContext("comment", body.get("comment"))
		.information("Request : saveEvent");
		Log
		.forContext("MemberName", "saveComment")
		.forContext("Service", appName)
		.forContext("event", body.get("comment"))
		.information("Request : saveComment");
		new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(body.get("comment").getBytes(ENCODE),"postComment");
		return "{\"response\":\"success\"}";
	}
        
}
