package com.service.person;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PersonRepository extends CrudRepository<Person, Integer>{

	Person findByPersonID(int id);
	
	List<Person> findAll();
}
