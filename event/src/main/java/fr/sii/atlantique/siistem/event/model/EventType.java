package fr.sii.atlantique.siistem.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document(collection = "eventtypes")
public class EventType implements Serializable {

	@Id
	@JsonProperty("EventTypeId")
	private String id;

	@JsonProperty("Type")
	private String label;
	
}