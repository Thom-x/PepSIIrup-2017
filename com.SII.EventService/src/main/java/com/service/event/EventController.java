package com.service.event;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class EventController {
	
	/*@Autowired
	private EventRepository repository;*/
	
	@RequestMapping(value = "/coucou", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public Event coucou() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/hh/mm");
		Date ds = sdf.parse("12/03/2016/12/00");
		Date df = sdf.parse("12/03/2016/14/00");
		//System.out.println(repository);
		//List<Event> events = repository.findAll();
		//System.out.println(events.get(0).getDescription() + " start: " + events.get(0).getDateStart() + " - " + events.get(0).getDateEnd());
		return new Event("Foot en salle", ds ,df, "x45dr84ww64vg", "Seance intensive, venez motivé!", 0, 1);
	}
	
	//mocked
	@RabbitListener(queues = "#{eventQueue.name}")
	 public String getAllEvent(int id) throws InterruptedException {
		return "Afterwork hangar a bananes, Foot en salle urban soccer, dej tech big data";
	}
}
