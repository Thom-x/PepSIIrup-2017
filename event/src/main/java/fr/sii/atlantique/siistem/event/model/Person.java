package fr.sii.atlantique.siistem.event.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Person {

    @JsonIgnore
    private String id;

    @JsonProperty("PersonEmail")
    private String email;

    @JsonProperty("Pseudo")
    private String pseudo;

}
