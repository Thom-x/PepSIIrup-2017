package fr.sii.atlantique.siistem.personne.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.sii.atlantique.siistem.personne.model.Person;

/**
 * Repository to use Database with the Person Service
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@Repository
@Transactional
public interface PersonRepository extends CrudRepository<Person, Integer>{

	Person findByPersonID(int id);
	
	Person findByPersonEmail(String email);
	
	List<Person> findAll();
}
