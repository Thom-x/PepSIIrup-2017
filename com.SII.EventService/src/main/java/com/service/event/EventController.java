package com.service.event;


import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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
	private static final String ENCODE = "UTF-8";
	

	@RequestMapping(value = "/coucou", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public Event coucou() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/hh/mm");
		Date ds = sdf.parse("13/04/2016/14/00");
		System.out.println("oui c'est tres le oui!   " + ds);
		Date df = sdf.parse("12/03/2016/14/00");
		/*List<Event> events = repository.getEventFromPlace("x12de45thj4", ds);
		for (Event event: events){
			if (ds.before(event.getDateStart())){
				System.out.println(ds + " before " + event.getDateStart());
			}else{
				System.out.println(ds + " after " + event.getDateStart());
			}
			System.out.println(event.getName() + " " + event.getDescription() + "\ndateD= " + event.getDateStart());
		}*/
		List<Event> events = repository.findByOwner(1);
		for(Event event : events){
			System.out.println(event.getName() + " " + event.getDescription());
		}
		return new Event("Foot en salle", ds ,df, "x45dr84ww64vg", "Seance intensive, venez motivé!", 0, 1);
	}
	
	@RabbitListener(queues = "#{saveEvent.name}")
	public String saveEvent(byte[] data){
		Event e = (Event)SerializationUtils.deserialize(data);
		repository.save(e);
		return "ok";
	}
	
	@RabbitListener(queues = "{findByOwner}")
	public String findByOwner(byte[] owner) throws UnsupportedEncodingException{
		return repository.findByOwner(Integer.parseInt(new String(owner,ENCODE))).toString();
	}
	
	@RabbitListener(queues = "{getEventByPlace}")
	public String getEventByPlace(byte[] place) throws UnsupportedEncodingException{
		return repository.getEventFromPlace(new String(place, ENCODE), new Date()).toString();
	}
}
