package com.service.client;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.service.event.Event;

/**
 * Rest Controller to use Comment Event
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@RestController
@Component
@CrossOrigin
public class WebEventController {

	private static final String ENCODE = "UTF-8";
	private static final String EXCHANGE = "exc.event";
	private static final Logger LOGGER = Logger.getLogger(WebEventController.class.getName());

	/**
	 * Method to find an Event by Owner with RabbitMq
	 * @param id
	 * @return
	 */
	@RequestMapping("/findByOwner")
	public String getEvent(@RequestParam(value="id", defaultValue="1") String id){
		String response = "";
		try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"findByOwner");
		} catch (UnsupportedEncodingException e) {
			LOGGER.log( Level.SEVERE, "findByOwner : an UnsupportedEncodingException was thrown", e);
		}
		return response;
	}

	/**
	 * Method to find an Event by Place with RabbitMq
	 * @param id
	 * @return
	 */
	@RequestMapping("/getEventByPlace")
	public String getAllEvent(@RequestParam(value="id", defaultValue="1") String id){
		String response = "";
		try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getEventByPlace");
		} catch (UnsupportedEncodingException e) {
			LOGGER.log( Level.SEVERE, "getEventByPlace: an UnsupportedEncodingException was thrown", e);
		}
		return response;
	}

	/**
	 * Method to save an event with RabbitMq
	 * @param id
	 * @return
	 */
	@RequestMapping("/saveEvent")
	public String updateEvent(@RequestParam(value="id", defaultValue="1") String id){
		Event e = new Event("test");
		return new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(e),"saveEvent");
	}

	/**
	 * Method to find all Events with RabbitMq
	 * @param id
	 * @return
	 */
	@RequestMapping("/getAllEvent")
	public String findAllEvent(@RequestParam(value="id", defaultValue="1") String id){
		String response ="";
		try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE), "getAllEvent");
		} catch (UnsupportedEncodingException e) {
			LOGGER.log( Level.SEVERE, "getAllEvent: an UnsupportedEncodingException was thrown", e);
		}
		return response;
	}

}
