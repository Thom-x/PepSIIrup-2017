package fr.sii.atlantique.siistem.client.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;
import java.util.Date;

@JsonRootName("Suggestion")
public class Suggestion implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonProperty("SuggestionId")
	private int suggestionId;
	
	@JsonProperty("Text")
	private String text;
	
	@JsonProperty("Job")
	private String job;
	
	@JsonProperty("Date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="CET")
	private Date date;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
