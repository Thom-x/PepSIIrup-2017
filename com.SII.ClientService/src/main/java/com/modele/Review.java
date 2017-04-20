package com.modele;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name="Review")
@JsonRootName("Review")
public class Review implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("PersonId")
	@Id
	@ManyToOne
	@JoinColumn(name = "Personid",unique=true, nullable=false)
	private Person personId;
	
	@JsonProperty("EventId")
	@Id
	@ManyToOne
	@JoinColumn(name = "EventId",unique=true, nullable=false)
	private Event eventId;
	
	@JsonProperty("Rate")
	@Column(name = "Rate")
	private int rate;
	
	@JsonProperty("Text")
	@Column(name = "Text")
	private int text;
	

	public Review(){
		//JPA need empty constructor
	}
	
	public Review(Person person, Event event){
		this.personId = person;
		this.eventId = event;
	}


	public Person getPersonId() {
		return personId;
	}

	public void setPersonId(Person personId) {
		this.personId = personId;
	}

	public Event getEventId() {
		return eventId;
	}

	public void setEventId(Event eventId) {
		this.eventId = eventId;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getText() {
		return text;
	}

	public void setText(int text) {
		this.text = text;
	}
	
	
	
}
