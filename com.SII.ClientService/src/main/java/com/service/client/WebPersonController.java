package com.service.client;

import java.io.UnsupportedEncodingException;
import org.apache.commons.lang.SerializationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.service.person.Person;


/**
 * Client controller, fetches Account info from the microservice via
 */
@RestController
@Component
@CrossOrigin
public class WebPersonController {
	
		private static final String ENCODE = "UTF-8";
		
	    @RequestMapping("/getPerson")
	    public String getPerson(@RequestParam(value="id", defaultValue="1") String id) throws InterruptedException, UnsupportedEncodingException{
	    	return new RabbitClient().rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getPerson");
		}
		
	    @RequestMapping("/getAllPerson")
	    public String getAllPerson(@RequestParam(value="id", defaultValue="1") String id) throws InterruptedException, UnsupportedEncodingException{
	    	return new RabbitClient().rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getAllPerson");
		}
	    
	    @RequestMapping("/addPerson")
	    public String addPerson(@RequestParam(value="name", defaultValue="Dorian") String name, @RequestParam(value="job", defaultValue="stagiaire") String job) throws InterruptedException{
	    	Person p = new Person(name,job);
	    	return new RabbitClient().rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(p),"addPerson");
		}
	    
	    
	  }

