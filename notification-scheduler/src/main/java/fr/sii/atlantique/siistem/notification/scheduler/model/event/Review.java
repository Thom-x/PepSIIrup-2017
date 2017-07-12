package fr.sii.atlantique.siistem.notification.scheduler.model.event;

import java.io.Serializable;

public class Review implements Serializable {

	private static final long serialVersionUID = 1L;
	private Person personId;
	private Event eventId;
	private int rate;
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
