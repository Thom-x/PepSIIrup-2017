package com.service.event;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.modele.Event;
import com.modele.Person;

/**
 * Repository of the Event service, to work with SQL Server
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@Repository
@Transactional
public interface EventRepository extends CrudRepository<Event, Integer>{
	

	Event findByName(String name);
	List<Event> findAll();
	@Query("select e From Event e where e.placeID = :placeID and e.dateStart > :dateStart")
	List<Event> getEventFromPlace(@Param("placeID") String placeID, @Param("dateStart") Date dateStart);
	List<Event> findByOwner(Person owner);
	@Query("select e From Event e ,Review r where e.eventId = r.eventId and r.personId = :person")
	List<Event> getEventsByPerson(@Param("person") Person person);
	@Query("select e From Event e where e.name LIKE CONCAT('%',:name,'%')")
	List<Event> searchEvent(@Param("name") String name);
	@Query("select e From Event e where e.dateStart > :dateStart ORDER BY e.dateStart ASC")
	List<Event> getUpcommingEvents(@Param("dateStart") Date dateStart, Pageable pageable);

}
