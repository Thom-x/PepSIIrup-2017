package com.eventService;

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
	
	List<Event> findAll();
	
	@Query("update Event e set e.texte = :name where e.id = :id")
	@Modifying
	void majName(@Param("name")String name, @Param("id")int id);
}
