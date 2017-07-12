package fr.sii.atlantique.siistem.client.model;

/**
 * Event Class with JPA
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

@JsonRootName("EventType")
public class EventType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@JsonProperty("EventTypeId")
	private int eventTypeId;
	
	@JsonProperty("Type")
	private String type;

	public int getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(int eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}