package fr.sii.atlantique.siistem.event.controller;

import fr.sii.atlantique.siistem.event.model.Event;
import fr.sii.atlantique.siistem.event.service.EventService;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {

    private final EventService eventService;
	
	public EventController(EventService eventService){
	    this.eventService = eventService;
	}

	@GetMapping
    public ResponseEntity getAll(@RequestParam(required = false) String ownerId,
								 @RequestParam(required = false) String name,
								 @RequestParam(required = false) boolean upComming) {
		List<Event> events;
		if (StringUtils.isNotBlank(name)) {
			events = eventService.searchEventsByName(name);
		} else if (StringUtils.isNotBlank(ownerId)) {
			events = eventService.getEventsByOwner(ownerId);
		}  else if (upComming) {
			events = eventService.getEventsUpcomming();
		} else {
			events = eventService.getEvents();
		}
		return ResponseEntity.ok(events);
	}

	@GetMapping("/{id}")
	public ResponseEntity getEvent(@PathVariable String id) {
		Event event = eventService.getEvent(id);
		return event != null ? ResponseEntity.ok(event) : ResponseEntity.notFound().build();
	}

    @GetMapping("/types")
    public ResponseEntity getAllEventTypes(){
		return ResponseEntity.ok(eventService.getEventTypes());
    }

}
