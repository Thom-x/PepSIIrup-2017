package com.service.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

/**
 * Rest Controller to use Review Service
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@RestController
@Component
@CrossOrigin
public class WebReviewController {

	private static final String ENCODE = "UTF-8";
	private static final String EXCHANGE = "exc.participant";
	@Value("${spring.application.name}")
	private String appName;

	public WebReviewController(){
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
				.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))	
				.createLogger());
	}


	/**
	 * Method to find a Review by id with RabbitMQ
	 * @param id
	 * @return
	 */
	@RequestMapping("/getReview")
	public String getReview(@RequestParam(value="id", defaultValue="1") String id){
		String response = "";
		try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getReview");
		} catch (UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getReview")
			.forContext("Service", appName)
			.error(e,"{date} UnsupportedEncodingException");
		}
		Log
		.forContext("id", id)
		.forContext("MemberName", "getReview")
		.forContext("Service", appName)
		.information("Request : getReview");
		return response;
	}

	/**
	 * Method to add a review with RabbitMQ
	 * @param review
	 * @return
	 */
	@RequestMapping(value = "/addReview", method = RequestMethod.POST)
	public String addPerson(@RequestParam Map<String, String> body){
		String review = body.get("review");
		String res = null;
		GoogleIdToken idToken = OauthTokenVerifier.checkGoogleToken(body.get("tokenid"));
		if (idToken != null) {
			Log
			.forContext("person", review)
			.forContext("MemberName", "updateReview")
			.forContext("Service", appName)
			.information("Request : updateReview");
			try {
				res =  new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(review.getBytes(ENCODE),"addReview");
			} catch (UnsupportedEncodingException e) {
				Log
				.forContext("MemberName", "saveParticipant")
				.forContext("Service", appName)
				.error(e,"UnsupportedEncodingException");
			}
		} else {
			Log
			.forContext("Service", appName)
			.information("Invalid Token");
			return "{\"response\":\"error\"}";
		}
		return res;
	}

	/**
	 * Method to update a review with RabbitMQ
	 * @param review
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(value = "/updateReview", method = RequestMethod.POST)
	public String updateReview(@RequestParam Map<String, String> body) throws JsonParseException, JsonMappingException, IOException{
		String res;
		GoogleIdToken idToken = OauthTokenVerifier.checkGoogleToken(body.get("tokenid"));
		if (idToken != null) {
			Log
			.forContext("person", body.get("review"))
			.forContext("MemberName", "updateReview")
			.forContext("Service", appName)
			.information("Request : updateReview");
				res = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(body.get("review").getBytes(ENCODE),"updateReview");
		} else {
			Log
			.forContext("Service", appName)
			.information("Invalid Token");
			return "{\"response\":\"error\"}";
		}
		return res;
	}

}
