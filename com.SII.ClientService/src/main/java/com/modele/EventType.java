package com.modele;

/**
 * Event Class with JPA
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name="Eventtype")
@JsonRootName("EventType")
public class EventType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@JsonProperty("EventTypeId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Eventtypeid",unique=true, nullable=false)
	private int eventTypeId;
	
	@JsonProperty("Type")
	@Column(name = "Type")
	private String type;
	
	public EventType(){
		//JPA need empty constructor
	} 

	public EventType(int eventTypeId, String type) {
		super();
		this.eventTypeId = eventTypeId;
		this.type = type;
	}
	
}