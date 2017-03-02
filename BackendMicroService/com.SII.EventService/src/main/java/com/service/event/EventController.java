package com.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
	
	@Autowired
	private EventRepository repository;
	
	@RequestMapping(value = "/coucou", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public Event coucou(){
		Event event = new Event("sing", "coucou depuis l'autre côté!");
        //Event event = new Event("sing", "coucou depuis l'autre côté!");
		System.out.println(event.getId());
		repository.save(event);
		Event event2 = new Event("sing2", "coucou depuis l'autre côté!2");
		repository.save(event2);

		return event;
	}
}
