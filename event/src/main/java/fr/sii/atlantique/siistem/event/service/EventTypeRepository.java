package fr.sii.atlantique.siistem.event.service;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.sii.atlantique.siistem.event.model.EventType;

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