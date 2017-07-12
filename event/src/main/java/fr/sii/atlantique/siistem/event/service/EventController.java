package fr.sii.atlantique.siistem.event.service;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.SerializationUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.sii.atlantique.siistem.event.model.Event;
import fr.sii.atlantique.siistem.event.model.EventType;
import fr.sii.atlantique.siistem.event.model.Person;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import serilogj.Log;
import serilogj.LoggerConfiguration;
import serilogj.core.LoggingLevelSwitch;
import serilogj.events.LogEventLevel;
import serilogj.sinks.seq.SeqSink;

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
	@Autowired
	private EventTypeRepository eventTypeRepository;
	private static final String ENCODE = "UTF-8";
	@Value("${spring.application.name}")
	private String appName;
	
	public EventController(){
		LoggingLevelSwitch levelswitch = new LoggingLevelSwitch(LogEventLevel.Verbose);
		Log.setLogger(new LoggerConfiguration()		
		.writeTo(new SeqSink(Constants.getINSTANCE().getLogserverAddr(), Constants.getINSTANCE().getLogserverApikey(), null, Duration.ofSeconds(2), null, levelswitch))	
		.createLogger());
	}

	/**
	 * method to save an event in the DB
	 * @param data
	 * @return
	 * @throws JsonProcessingException
	 */
	@RabbitListener(queues = "#{saveEventQueue.name}")
	public String saveEvent(byte[] data){
		String res = null;
		Event e = (Event)SerializationUtils.deserialize(data);
		Event event = null;
		if (e.checkEvent()){
			 event = repository.save(e);
		}
		else{
			Log
			.forContext("MemberName", "saveEvent")
			.forContext("Service", appName)
			.error(new IllegalArgumentException(),"IllegalArgumentException");
		}
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("MemberName", "saveEvent")
		.forContext("Service", appName)
		.information("RabbitMQ : saveEvent");
		try {
			res =  mapper.writeValueAsString(event);
		} catch (JsonProcessingException e1) {
			Log
			.forContext("MemberName", "saveEvent")
			.forContext("Service", appName)
			.error(e1,"JsonProcessingException");
		}
		return res;
	}

	/**
	 * method to find all events by  owner
	 * @param owner
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RabbitListener(queues = "#{findByOwnerQueue.name}")
	public String findByOwner(byte[] owner){
		
		Log
		.forContext("MemberName", "findByOwner")
		.forContext("Service", appName)
		.information("RabbitMQ : findByOwner");
		return repository.findByOwner((Person)SerializationUtils.deserialize(owner)).toString();
	}
	
	/**
	 * method to find all event of a person
	 * @param owner
	 * @return
	 * @throws JsonProcessingException 
	 * @throws UnsupportedEncodingException
	 */
	@RabbitListener(queues = "#{getEventsByPersonQueue.name}")
	public String getEventsByPerson(byte[] person) throws JsonProcessingException{
		List<Event> res;
		Log
		.forContext("MemberName", "getEventsByPerson")
		.forContext("Service", appName)
		.information("RabbitMQ : getEventsByPerson");
		res = repository.getEventsByPerson((Person)SerializationUtils.deserialize(person));
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(res);
	}

	/**
	 * method to get an event by his place
	 * @param place
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RabbitListener(queues = "#{getEventByPlaceQueue.name}")
	public String getEventByPlace(byte[] place){
		String res = "";
		Log
		.forContext("MemberName", "getEventByPlace")
		.forContext("Service", appName)
		.information("RabbitMQ : getEventByPlace");
		try {
			res = repository.getEventFromPlace(new String(place, ENCODE), new Date()).toString();
		} catch (UnsupportedEncodingException e1) {
			Log
			.forContext("MemberName", "getEventByPlace")
			.forContext("Service", appName)
			.error(e1,"UnsupportedEncodingException");
		}
		return res;
	}
	
	/**
	 * method to get all the events
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@RabbitListener(queues = "#{getAllEventQueue.name}")
	public String getAllEvent(byte[] id){
		String res = "";
		List<Event> events = repository.findAll();
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("MemberName", "getAllEvent")
		.forContext("Service", appName)
		.information("RabbitMQ : getAllEvent");
		try {
			res = mapper.writeValueAsString(events);
		} catch (JsonProcessingException e1) {
			Log
			.forContext("MemberName", "getAllEvent")
			.forContext("Service", appName)
			.error(e1,"JsonProcessingException");
		}
		return res;
	}
	
	/**
	 * method to get all the events type 
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 */
	@RabbitListener(queues = "#{getAllEventTypeQueue.name}")
	public String getAllEventType(byte[] id){
		String res = "";
		List<EventType> eventsType = eventTypeRepository.findAll();
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("MemberName", "getAllEventType")
		.forContext("Service", appName)
		.information("RabbitMQ : getAllEventType");
		try {
			res = mapper.writeValueAsString(eventsType);
		} catch (JsonProcessingException e1) {
			Log
			.forContext("MemberName", "getAllEventType")
			.forContext("Service", appName)
			.error(e1,"JsonProcessingException");
		}
		return res;
	}
	
	/**
	 * method to search events by name/description/owner using fuzzi distance
	 * @param name
	 * @return
	 */
	@RabbitListener(queues = "#{searchEventByNameQueue.name}")
	public String searchEventByName(byte[] name){
		String res = null;
		List<Event> events = null;
		List<Event> resEvents = new ArrayList<>() ;
		SortedMap<Integer, ArrayList<Event>> indexedEvents = new TreeMap<Integer,  ArrayList<Event>>();
		try {
			Log
			.forContext("MemberName", "searchEventByName")
			.forContext("Service", appName)
			.forContext("name",new String(name, ENCODE))
			.information("RabbitMQ : searchEventByName");
		} catch (UnsupportedEncodingException e2) {
			Log
			.forContext("MemberName", "searchEventByName")
			.forContext("Service", appName)
			.error(e2,"UnsupportedEncodingException");
		}
		events = repository.findAll();
		
		for (Event e : events){
			try {
				int fuzzyName =FuzzySearch.tokenSetPartialRatio(e.getName(),new String(name, ENCODE));
				int fuzzyDescr =FuzzySearch.tokenSetPartialRatio(e.getDescription(),new String(name, ENCODE));
				int fuzzyOwner =FuzzySearch.tokenSetPartialRatio(e.getOwner().getPseudo(),new String(name, ENCODE));
				if (fuzzyName>70 || fuzzyDescr>80 || fuzzyOwner>90){
					int key = Math.max(Math.max(fuzzyName,fuzzyDescr),fuzzyOwner);
					List<Event> itemsList = indexedEvents.get(key);
					if(itemsList == null) {
					      itemsList = new ArrayList<>();
					      itemsList.add(e);
					      indexedEvents.put(key, (ArrayList<Event>) itemsList);
					}
					else{
						 itemsList.add(e);
					 }
				}
			} catch (UnsupportedEncodingException e1) {
				Log
				.forContext("MemberName", "searchEventByName")
				.forContext("Service", appName)
				.error(e1,"UnsupportedEncodingException");
			}
		}
		for(int i : indexedEvents.keySet()){
			for( Event eventToSend : indexedEvents.get(i)){
				resEvents.add(eventToSend);
			}
		}
		Collections.reverse(resEvents);
		ObjectMapper mapper = new ObjectMapper();
		Log
		.forContext("MemberName", "searchEventByName")
		.forContext("Service", appName)
		.information("RabbitMQ : searchEventByName");
		try {
			res = mapper.writeValueAsString(resEvents);
		} catch (JsonProcessingException e1) {
			Log
			.forContext("MemberName", "searchEventByName")
			.forContext("Service", appName)
			.error(e1,"JsonProcessingException");
		}
		return res;
	}
	

	/**
	 * method to get all upcomming events
	 * @param id
	 * @return
	 * @throws JsonProcessingException
	 * @throws UnsupportedEncodingException 
	 * @throws NumberFormatException 
	 */
	@RabbitListener(queues = "#{getUpcommingEventsQueue.name}")
	public String getUpcommingEvents(byte[] id){
		String res = "";
		int num = -1;
		try {
			num = Integer.parseInt(new String(id, ENCODE));
		} catch (NumberFormatException | UnsupportedEncodingException e) {
			Log
			.forContext("MemberName", "getUpcommingEvents")
			.forContext("Service", appName)
			.error(e,"Exception");
		}
		Pageable p = new PageRequest(0,num);
		List<Event> events = repository.getUpcommingEvents(new Date(), p);
		Log
		.forContext("MemberName", "getUpcommingEvents")
		.forContext("Service", appName)
		.information("RabbitMQ : getUpcommingEvents");
		ObjectMapper mapper = new ObjectMapper();
		try {
			res = mapper.writeValueAsString(events);
		} catch (JsonProcessingException e) {
			Log
			.forContext("MemberName", "getUpcommingEvents")
			.forContext("Service", appName)
			.error(e,"JsonProcessingException");
		}
		return res;
	}	
	
}
