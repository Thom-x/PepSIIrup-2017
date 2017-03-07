package com.service.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class EventController {
	
	@Autowired
	private EventRepository repository;
	
	@RequestMapping(value = "/coucou", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public Event coucou(){
		Event returnVal = new Event("sing", "coucou depuis l'autre côté!");
		/*List<Event> events = repository.findAll();
		for(Event event : events){
			System.out.println("id: " + event.getId() + " Name: " + event.getName() + " Texte: " + event.getTexte());
		}*/
			
		return returnVal;
	}
}
