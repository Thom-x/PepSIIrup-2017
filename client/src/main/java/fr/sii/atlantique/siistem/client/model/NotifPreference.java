package fr.sii.atlantique.siistem.client.model;

/**
 * Event Class with JPA
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("Notifpreference")
public class NotifPreference implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@JsonProperty("NotifTypeId")

	private int notifTypeId;
	
	@JsonProperty("Type")
	private String type;
	
	public NotifPreference(){
		//JPA need empty constructor
	} 

	public NotifPreference(int notifTypeId, String type) {
		super();
		this.notifTypeId = notifTypeId;
		this.type = type;
	}
	
	
	
}