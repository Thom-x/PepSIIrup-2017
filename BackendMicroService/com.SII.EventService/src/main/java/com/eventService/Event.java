package com.eventService;


import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonRootName;
//@Column(name = "id",unique=true, nullable=false)
@Entity
@Table(name="Event")
@JsonRootName("Event")
public class Event implements Serializable{
	
	public Event(String name, String texte){
		this.name = name;
		this.texte = texte;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",unique=true, nullable=false)
	private int id;

	public int getId() {
		return this.id;
	}
	
	@Column(name = "name")
	private String name;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "texte")
	private String texte;
	
	public String getTexte(){
		return this.texte;
	}
	
	public void setTexte(String texte) {
		this.texte = texte;
	}
	
	@Override
	public String toString() {
		return name + ": " + texte;
	}
}
