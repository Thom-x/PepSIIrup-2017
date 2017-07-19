package fr.sii.atlantique.siistem.personne.controller;

import fr.sii.atlantique.siistem.personne.model.Person;
import fr.sii.atlantique.siistem.personne.service.PersonService;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false) String email) {
        if (StringUtils.isBlank(email)) {
            return ResponseEntity.ok(personService.getAll());
        } else {
            Person person = personService.getByEmail(email);
            return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        Person result = personService.getById(id);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

}
