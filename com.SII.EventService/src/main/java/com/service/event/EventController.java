package com.service.event;


import java.io.UnsupportedEncodingException;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Controller of the Event Service
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */

@RestController
@Component
public class EventController {

	@Autowired
	private EventRepository repository;
	private static final String ENCODE = "UTF-8";

	@RabbitListener(queues = "#{saveEventQueue.name}")
	public String saveEvent(byte[] data){
		Event e = (Event)SerializationUtils.deserialize(data);
		repository.save(e);
		return "ok";
	}

	@RabbitListener(queues = "#{findByOwnerQueue.name}")
	public String findByOwner(byte[] owner) throws UnsupportedEncodingException{
		return repository.findByOwner(Integer.parseInt(new String(owner,ENCODE))).toString();
	}

	@RabbitListener(queues = "#{getEventByPlaceQueue.name}")
	public String getEventByPlace(byte[] place) throws UnsupportedEncodingException{
		return repository.getEventFromPlace(new String(place, ENCODE), new Date()).toString();
	}
	
	@RabbitListener(queues = "#{getAllEventQueue.name}")
	public String getAllEvent(byte[] id) throws JsonProcessingException{
		List<Event> events = repository.findAll();
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
		return mapper.writeValueAsString(events);
	}

}
