package com.service.client;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * Client controller, fetches Account info from the microservice via
 */
@RestController
@Component
public class WebEventController {

	
	@Autowired
	private RabbitTemplate template;

	@Autowired
	private DirectExchange direct;
	
    
    @RequestMapping("/getEvent")
    public String getEvent(@RequestParam(value="id", defaultValue="1") String id) {
		String response = (String) template.convertSendAndReceive(direct.getName(), "event",id);
		System.out.println(response);
		return response;
    }
	
}
