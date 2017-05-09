package com.service.event;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.modele.EventType;

/**
 * Repository of the Event service, to work with SQL Server
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@Repository
@Transactional
public interface EventTypeRepository extends CrudRepository<EventType, Integer>{


	List<EventType> findAll();

}