package com.service.event;


import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonRootName;
//@Column(name = "id",unique=true, nullable=false)
@Entity
@Table(name="Event")
@JsonRootName("Event")
public class Event implements Serializable{
	

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",unique=true, nullable=false)
	private int eventId;
	@Column(name = "name")
	private String name;
	@Column(name = "texte")
	private String texte;
	
	public Event(){}
	
	public Event(String name, String texte){
		this.name = name;
		this.texte = texte;
	}
	
	public int getId() {
		return this.eventId;
	}
	
	public void setId(int id){
		this.eventId = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

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
