package com.service.client;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	private static final Logger LOGGER = Logger.getLogger(WebCommentController.class.getName());
	
	/**
	 * Method to get Participant by Event with RabbitMq
	 * @param id
	 * @return
	 */
    @RequestMapping("/getAllParticipantById")
    public String getParticipant(@RequestParam(value="id", defaultValue="1") String id){
    	String response = "";
    	try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getAllParticipantById");
		} catch (UnsupportedEncodingException e) {
			LOGGER.log( Level.SEVERE, "an exception was thrown", e);
		}
    	return response;
    }
}
