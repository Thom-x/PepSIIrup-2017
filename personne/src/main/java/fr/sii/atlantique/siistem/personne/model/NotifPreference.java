package fr.sii.atlantique.siistem.personne.model;

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
@Table(name="Notifpreference")
@JsonRootName("Notifpreference")
public class NotifPreference implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@JsonProperty("NotifTypeId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",unique=true, nullable=false)
	private int notifTypeId;
	
	@JsonProperty("Type")
	@Column(name = "preference")
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