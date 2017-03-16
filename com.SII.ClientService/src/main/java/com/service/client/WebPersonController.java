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
import com.service.person.Person;


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
	@RequestMapping("/addPerson")
	public String addPerson(@RequestParam(value="name", defaultValue="Dorian") String name, @RequestParam(value="job", defaultValue="stagiaire") String job){
		Person p = new Person(name,job);
		return new RabbitClient(EXCHANGE).rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(p),"addPerson");
	}

}
