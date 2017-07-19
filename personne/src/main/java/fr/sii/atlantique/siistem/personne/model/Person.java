package fr.sii.atlantique.siistem.personne.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "person")
public class Person {

    @Id
    private int id;

    private String nom, prenom;

    @Indexed(unique = true)
    private String email;

    private int age;
}
