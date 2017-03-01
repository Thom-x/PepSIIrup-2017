package com.eventService;


import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name="Event")
@JsonRootName("Account")
public class Event implements Serializable{

	public Event(){
	}
	
	public Event(String name, String texte){
		this.name = name;
		this.texte = texte;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
