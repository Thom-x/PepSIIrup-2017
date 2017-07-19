package fr.sii.atlantique.siistem.personne.repository;

import fr.sii.atlantique.siistem.personne.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {

    Person findByEmail(String email);
}