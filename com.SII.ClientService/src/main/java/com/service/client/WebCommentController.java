package com.service.client;

import java.io.UnsupportedEncodingException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Client controller, fetches Account info from the microservice via
 */
@RestController
@Component
@CrossOrigin
public class WebCommentController {

		
	
	private static final String ENCODE = "UTF-8";
	
    @RequestMapping("/getCommentByEvent")
    public String getComment(@RequestParam(value="id", defaultValue="1") String id) throws UnsupportedEncodingException {
    	return new RabbitClient().rabbitRPCRoutingKeyExchange(id.getBytes(ENCODE),"getCommentByEvent");
    }
	
}
