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
	private int personId;
	
	@JsonProperty("EventId")
	@Id
	@ManyToOne
	@JoinColumn(name = "EventId",unique=true, nullable=false)
	private int eventId;
	
	@JsonProperty("Rate")
	@Column(name = "Rate")
	private int rate;
	
	@JsonProperty("Text")
	@Column(name = "Text")
	private String text;
	

	public Review(){
		//JPA need empty constructor
	}


	public int getPersonId() {
		return personId;
	}



	public void setPersonId(int personId) {
		this.personId = personId;
	}



	public int getEventId() {
		return eventId;
	}



	public void setEventId(int eventId) {
		this.eventId = eventId;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	
	
}
