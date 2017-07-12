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

@JsonRootName("Comment")
public class Comment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@JsonProperty("CommentId")
	private int commentId;
	
	@JsonProperty("ResponseTo")
	private int responseTo;
	
	@JsonProperty("Text")
	private String text;
	
	@JsonProperty("DatePost")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="CET")
	private Date datePost;
	
	@JsonProperty("EventId")
	private int eventId;
	
	@JsonProperty("PersonId")
	private int personId;
	
	@JsonProperty("Person")
	private Person person;

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