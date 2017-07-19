package fr.sii.atlantique.siistem.personne.service;

import fr.sii.atlantique.siistem.personne.model.Person;
import fr.sii.atlantique.siistem.personne.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repository;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    public List<Person> getAll() {
        return repository.findAll();
    }

    public Person getById(String id) {
        return repository.findOne(id);
    }

    public Person getByEmail(String email) {
        return repository.findByEmail(email);
    }

}
