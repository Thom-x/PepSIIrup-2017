package fr.sii.atlantique.siistem.event.service;

import fr.sii.atlantique.siistem.event.model.Event;
import fr.sii.atlantique.siistem.event.model.EventType;
import fr.sii.atlantique.siistem.event.model.Person;
import fr.sii.atlantique.siistem.event.repository.EventRepository;
import fr.sii.atlantique.siistem.event.repository.EventTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    private final EventTypeRepository eventTypeRepository;

    public EventService(EventRepository eventRepository, EventTypeRepository eventTypeRepository) {
        this.eventRepository = eventRepository;
        this.eventTypeRepository = eventTypeRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public Event getEvent(String id) {
        return eventRepository.findOne(id);
    }

    public List<EventType> getEventTypes(){
        return eventTypeRepository.findAll();
    }

    public List<Event> getEventsByOwner(String ownerId) {
        Person owner = new Person();
        owner.setId(ownerId);
        return eventRepository.findByOwner(owner);
    }

    public List<Event> getEventsUpcomming(){
        return eventRepository.findByDateStartAfter(new Date());
    }

    public List<Event> searchEventsByName(String name) {
        return eventRepository.findByNameLike(name);
    }

}
