package com.modele;

/**
 * Event Class with JPA
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name="Comment")
@JsonRootName("Comment")
public class Comment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@JsonProperty("CommentId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CommentId",unique=true, nullable=false)
	private int commentId;
	
	@JsonProperty("ResponseTo")
	@Column(name = "Responseto")
	private int responseTo;
	
	@JsonProperty("Text")
	@Column(name = "Text")
	private String text;
	
	@JsonProperty("DatePost")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="CET")
	@Column(name = "Datepost")
	private Date datePost;
	
	@JsonProperty("EventId")
	@Column(name = "Eventid")
	private int eventId;
	
	@JsonProperty("PersonId")
	@Column(name = "Personid")
	private int personId;
	
	@JsonProperty("Person")
	@ManyToOne
	@JoinColumn(name = "Person")
	private Person person;
	
	
	public Comment(){
		//JPA need empty constructor
	}

	public Comment(int commentId, int responseTo, String text, Date datePost, int eventId, int personId, Person person) {
		super();
		this.commentId = commentId;
		this.responseTo = responseTo;
		this.text = text;
		this.datePost = datePost;
		this.eventId = eventId;
		this.personId = personId;
		this.person = person;
	}

	public int getCommentId() {
		return commentId;
	}


	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}


	public int getResponseTo() {
		return responseTo;
	}


	public void setResponseTo(int responseTo) {
		this.responseTo = responseTo;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public Date getDatePost() {
		return datePost;
	}


	public void setDatePost(Date datePost) {
		this.datePost = datePost;
	}


	public int getEventId() {
		return eventId;
	}


	public void setEventId(int eventId) {
		this.eventId = eventId;
	}


	public int getPersonId() {
		return personId;
	}


	public void setPersonId(int personId) {
		this.personId = personId;
	}


	public Person getPerson() {
		return person;
	}


	public void setPerson(Person person) {
		this.person = person;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	} 
	
	
}