package fr.sii.atlantique.siistem.client.security;

import lombok.Data;

import java.util.List;

@Data
public class Person {

    private String email;

    private String password;

    private List<String> roles;
}
