package com.service.client;



import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.service.event.Event;

/**
 * Client controller, fetches Account info from the microservice via
 */
@RestController
@Component
@CrossOrigin
public class WebEventController {

	private static final String ENCODE = "UTF-8";
	

    @RequestMapping("/findByOwner")
    public String getEvent(@RequestParam(value="id", defaultValue="1") String id) throws InterruptedException, UnsupportedEncodingException {
    	return new RabbitClient().rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"findByOwner");
	}
    
    @RequestMapping("/getEventByPlace")
    public String getAllEvent(@RequestParam(value="id", defaultValue="1") String id) throws InterruptedException, UnsupportedEncodingException {
    	return new RabbitClient().rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getEventByPlace");
	}
    
    @RequestMapping("/saveEvent")
    public String updateEvent(@RequestParam(value="id", defaultValue="1") String id) throws InterruptedException, UnsupportedEncodingException {
    	Event e = new Event("test");
    	return new RabbitClient().rabbitRPCRoutingKeyExchange(SerializationUtils.serialize(e),"saveEvent");
	}

}
