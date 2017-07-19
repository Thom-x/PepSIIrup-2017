package fr.sii.atlantique.siistem.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "eventtypes")
@JsonRootName("EventType")
public class EventType {

	@Id
	@JsonProperty("EventTypeId")
	private String id;

	@JsonProperty("Type")
	private String label;
	
}