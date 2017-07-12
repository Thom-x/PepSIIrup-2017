package fr.sii.atlantique.siistem.suggestion;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity
@Table(name="Suggestion")
@JsonRootName("Suggestion")
public class Suggestion implements Serializable{

	private static final long serialVersionUID = 1L;
	@JsonProperty("SuggestionId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Suggestionid",unique=true, nullable=false)
	private int suggestionId;
	
	@JsonProperty("Text")
	@Column(name = "Text", nullable = false)
	private String text;
	
	@JsonProperty("Job")
	@Column(name = "Job", nullable = false)
	private String job;
	
	@JsonProperty("Date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="CET")
	@Column(name = "Date", nullable = false)
	private Date date;
	
	public Suggestion(){
		//JPA need empty constructor
	}

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
