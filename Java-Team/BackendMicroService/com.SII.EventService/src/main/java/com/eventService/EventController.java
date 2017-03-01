package com.eventService;

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
		repository.save(event);
		System.out.println("Bien le bonjour utilisateur." + repository);
		Event e = repository.findByName("sing");
		repository.majName("Adele", 1);
		System.out.println("Recup: " + e.getId() + " " + e.getName() + " " + e.getTexte()); 
		/*e = repository.findByName("Adele");
		System.out.println("Recup: " + e.getId() + " " + e.getName() + " " + e.getTexte());*/
		Event e2 = repository.findByName("non");
		System.out.println(e2);
		return event;
	}
}
