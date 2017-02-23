package com.eventService.event;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

	@RequestMapping(value = "/coucou", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public Event coucou(){
		Event event = new Event(1, "coucou depuis l'autre côté!");
		return event;
	}
}
