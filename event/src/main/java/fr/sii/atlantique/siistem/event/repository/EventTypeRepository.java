package fr.sii.atlantique.siistem.event.repository;

import fr.sii.atlantique.siistem.event.model.EventType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventTypeRepository extends MongoRepository<EventType, String> {

}