package fr.sii.atlantique.siistem.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "events")
public class Event implements Serializable {

	@Id
	@JsonProperty("EventId")
	private String id;
	
	@JsonProperty("Name")
	@Indexed(background = true)
	private String name;
	
	@JsonProperty("DateStart")
	private Date dateStart;
	
	@JsonProperty("DateEnd")
	private Date dateEnd;
	
	@JsonProperty("PlaceId")
	private String placeId;
	
	@JsonProperty("Description")
	private String description;
	
	@JsonProperty("Image")
	private String image;
	
	@JsonProperty("IsCanceled")
	private boolean canceled;
	
	@JsonProperty("Owner")
	private Person owner;
	
	@JsonProperty("EventType")
	private EventType eventType;

	@JsonProperty("Comments")
	private List<Comment> comments;

	@JsonProperty("Participants")
	private List<Person> participants;
}
