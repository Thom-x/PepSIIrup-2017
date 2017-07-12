package fr.sii.atlantique.siistem.notification.scheduler.model.event;

/**
 * Event Class with JPA
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
import java.io.Serializable;

public class EventType implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int eventTypeId;
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