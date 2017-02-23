package com.eventService.event;


import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Account")
public class Event {

	private String texte;
	private int id;
	
	public Event(String texte){
		this.texte = texte;
	}
	
	public Event(int id, String texte){
		this.id = id;
		this.texte = texte;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTexte(){
		return this.texte;
	}
	
	public void setTexte(String texte) {
		this.texte = texte;
	}
	
	@Override
	public String toString() {
		return "Event" + " [" + id + "]: $" + texte;
	}
}
