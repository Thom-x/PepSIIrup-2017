package com.service.event;


import java.io.Serializable;
import java.util.Date;

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
	@Column(name = "Eventid",unique=true, nullable=false)
	private int eventId;
	@Column(name = "Name")
	private String name;
	@Column(name = "Datestart")
	private Date dateStart;
	@Column(name = "Dateend")
	private Date dateEnd;
	@Column(name = "Placeid")
	private String placeID;
	@Column(name = "Description")
	private String description;
	@Column(name = "Image")
	private String image;
	@Column(name = "Iscanceled")
	private int isCanceled;
	@Column(name = "Owner")
	private int owner;
	
	
	public Event(){}
	
	public Event(String name) {
		this.name = name;
		this.dateStart = new Date();
		this.dateEnd = new Date();
		this.placeID = "17";
		this.description = "too long";
		this.isCanceled = 0;
		this.owner = 2;
	}
	
	
	public Event(String name, Date dateStart, Date dateEnd, String placeID, String description, int isCanceled,
			int owner) {
		super();
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.placeID = placeID;
		this.description = description;
		this.isCanceled = isCanceled;
		this.owner = owner;
	}

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

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Event){
		Event event = (Event)obj;
		if (event.getDateStart().equals(this.dateStart) && event.getDateEnd().equals(this.dateEnd)){
			return true;
		}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", name=" + name + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd
				+ ", placeID=" + placeID + ", description=" + description + ", image=" + image + ", isCanceled="
				+ isCanceled + ", owner=" + owner + "]";
	}
	
	
}
