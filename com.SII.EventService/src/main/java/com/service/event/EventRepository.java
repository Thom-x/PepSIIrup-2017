package com.service.event;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EventRepository extends CrudRepository<Event, Integer>{

	Event findByName(String name);
	
	//@Query("update Event e set e.texte = :texte where e.id = :id")
	List<Event> findAll();
	
	@Query("select e From Event e where e.placeID = :placeID and e.dateStart > :dateStart")
	List<Event> getEventFromPlace(@Param("placeID") String placeID, @Param("dateStart") Date dateStart);
	
	List<Event> findByOwner(int owner);
	
}
