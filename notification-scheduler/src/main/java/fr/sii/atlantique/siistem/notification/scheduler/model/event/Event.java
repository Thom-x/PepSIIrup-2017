package fr.sii.atlantique.siistem.notification.scheduler.model.event;

/**
 * Event Class with JPA
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4969993994153159255L;

	private int eventId;
	private String name;
	private Date dateStart;
	private Date dateEnd;
	private String placeID;
	private String description;
	private String image;
	private int isCanceled;
	private Person owner;
	private EventType eventType;

	public Event(){
		//JPA need empty constructor
	}

	public Event(String name) {
		this.name = name;
		this.dateStart = new Date();
		this.dateEnd = new Date();
		this.placeID = "17";
		this.description = "too long";
		this.isCanceled = 0;
		this.owner = new Person();
		this.image = "";
	}

	public Event(String name, Date dateStart, Date dateEnd, String placeID, String description, int isCanceled, String image, Person owner) {
		super();
		this.name = name;
		this.dateStart = dateStart;
		this.dateEnd = dateEnd;
		this.placeID = placeID;
		this.description = description;
		this.isCanceled = isCanceled;
		this.image = image;
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

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", name=" + name + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd
				+ ", placeID=" + placeID + ", description=" + description + ", image=" + image + ", isCanceled="
				+ isCanceled + ", owner=" + owner + ", eventType=" + eventType + "]";
	}


}
