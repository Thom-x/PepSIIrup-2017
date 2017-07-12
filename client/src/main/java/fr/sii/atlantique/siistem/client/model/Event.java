package fr.sii.atlantique.siistem.client.model;

/**
 * Event Class with JPA
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;
import java.util.Date;

@JsonRootName("Event")
public class Event implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@JsonProperty("EventId")
	private int eventId;
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("DateStart")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="CET")
	private Date dateStart;
	
	@JsonProperty("DateEnd")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="CET")
	private Date dateEnd;
	
	@JsonProperty("PlaceId")
	private String placeID;
	
	@JsonProperty("Description")
	private String description;
	
	@JsonProperty("Image")
	private String image;
	
	@JsonProperty("IsCanceled")
	private int isCanceled;
	
	@JsonProperty("Owner")
	private Person owner;
	
	@JsonProperty("EventType")
	private EventType eventType;
	
	public int getEventId() {
		return eventId;
	}
	
	public void setEventId(int id){
		this.eventId = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getPlaceID() {
		return placeID;
	}

	public void setPlaceID(String placeID) {
		this.placeID = placeID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getIsCanceled() {
		return isCanceled;
	}

	public void setIsCanceled(int isCanceled) {
		this.isCanceled = isCanceled;
	}

	public Person getOwner() {
		return owner;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
		result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (dateEnd == null) {
			if (other.dateEnd != null)
				return false;
		} else if (!dateEnd.equals(other.dateEnd))
			return false;
		if (dateStart == null) {
			if (other.dateStart != null)
				return false;
		} else if (!dateStart.equals(other.dateStart))
			return false;
		return true;
	}

	public boolean checkEvent() {
		return name != null &&  dateStart != null && dateEnd != null && placeID != null && description != null && owner != null;
	}
	
}
