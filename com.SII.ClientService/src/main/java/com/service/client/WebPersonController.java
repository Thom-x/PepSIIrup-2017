package com.service.client;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modele.Person;


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
	private static final Logger LOGGER = Logger.getLogger(WebPersonController.class.getName());

	/**
	 * Method to find a person by id with RabbitMQ
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPerson")
	public String getPerson(@RequestParam(value="id", defaultValue="1") String id){
		String response = "";
		try {
			response = new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getPerson");
		} catch (UnsupportedEncodingException e) {
			LOGGER.log( Level.SEVERE, "an exception was thrown", e);
		}
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
			LOGGER.log( Level.SEVERE, "an exception was thrown", e);
		}
		return response;
	}

	/**
	 * Method to find add a person with RabbitMQ
	 * @param name
	 * @param job
	 * @return
	 */
	@RequestMapping(value = "/addPerson", method = RequestMethod.POST)
	public String addPerson(@RequestBody Person person){
		System.out.println(person);
		return new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(person),"addPerson");
	}

}
