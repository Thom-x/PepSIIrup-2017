package fr.sii.atlantique.siistem.event.repository;

import fr.sii.atlantique.siistem.event.model.Event;
import fr.sii.atlantique.siistem.event.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {

	@Query(value = "{}", fields = "{id : 1, name : 1, description : 1, dateStart : 1, dateEnd : 1, image : 1, eventType.label: 1, canceled : 1, placeId : 1}")
	List<Event> findAll();

	@Query(value = "{}", fields = "{id : 1, name : 1, description : 1, dateStart : 1, dateEnd : 1, image : 1, eventType.label: 1, canceled : 1, placeId : 1}")
	List<Event> findByNameLike(String name);

	@Query(value = "{}", fields = "{id : 1, name : 1, description : 1, dateStart : 1, dateEnd : 1, image : 1, eventType.label: 1, canceled : 1, placeId : 1}")
	List<Event> findByDateStartAfter(Date dateStart);

	@Query(value = "{}", fields = "{id : 1, name : 1, description : 1, dateStart : 1, dateEnd : 1, image : 1, eventType.label: 1, canceled : 1, placeId : 1}")
	List<Event> findByOwner(Person person);

}
