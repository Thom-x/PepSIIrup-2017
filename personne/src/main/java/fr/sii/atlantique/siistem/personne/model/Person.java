package fr.sii.atlantique.siistem.personne.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

import javax.persistence.*;


/**
 * Class Person, with JPA
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@Entity
@Table(name = "Person")
@JsonRootName("Person")
public class Person implements Serializable{

	private static final long serialVersionUID = 7525202378849284784L;

	@JsonProperty("PersonId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PersonID",unique=true, nullable=false)
	private int personID;

	@JsonProperty("Pseudo")
	@Column(name = "Pseudo") 
	private String pseudo;
	
	@JsonProperty("LastName")
	@Column(name = "Lastname") 
	private String lastName;
	
	@JsonProperty("FirstName")
	@Column(name = "Firstname")
	private String firstName;

	@JsonProperty("Job")
	@Column(name = "Job") 
	private String job;

	@JsonProperty("PersonEmail")
	@Column(name = "Personemail") 
	private String personEmail;
	
	@JsonProperty("Phone")
	@Column(name = "Phone") 
	private String personTelephone;
	
	@JsonProperty("MailNotif")
	@Column(name = "Mailnotif") 
	private Boolean mailNotif;
	
	@JsonProperty("TelNotif")
	@Column(name = "Telnotif") 
	private Boolean telNotif;
	
	@JsonProperty("IDpreference")
	@ManyToOne
	@JoinColumn(name = "Idpreference")
	private NotifPreference typeNotif;
	
	
	public Person(String pseudo, String job, String personTelephone ) {
		this.pseudo = pseudo;
		this.job = job;
		this.personTelephone = personTelephone;
	}

	public Person(){
		//JPA need empty constructor
	} 
	

	public Boolean getMailNotif() {
		return mailNotif;
	}

	public void setMailNotif(Boolean mailNotif) {
		this.mailNotif = mailNotif;
	}

	public Boolean getTelNotif() {
		return telNotif;
	}

	public void setTelNotif(Boolean telNotif) {
		this.telNotif = telNotif;
	}

	public int getPersonID(){
		return this.personID;
	}

	public void setPersonID(int id){
		this.personID = id;
	}

	public String getPseudo(){
		return this.pseudo;
	}

	public void setPseudo(String pseudo){
		this.pseudo = pseudo;
	}
	
	public String getLastName(){
		return this.lastName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public String getFirstName(){
		return this.firstName;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getJob(){
		return this.job;
	}

	public void setString(String job){
		this.job = job;
	}

	public String getPersonEmail() {
		return personEmail;
	}

	public void setPersonEmail(String personEmail) {
		this.personEmail = personEmail;
	}


	
	public String getPersonTelephone() {
		return personTelephone;
	}

	public void setPersonTelephone(String personTelephone) {
		this.personTelephone = personTelephone;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public NotifPreference getTypeNotif() {
		return typeNotif;
	}

	public void setTypeNotif(NotifPreference typeNotif) {
		this.typeNotif = typeNotif;
	}

	@Override
	public String toString() {
		return "Person [personID=" + personID + ", pseudo=" + pseudo + ", lastName=" + lastName + ", firstName="
				+ firstName + ", job=" + job + ", personEmail=" + personEmail + ", personTelephone=" + personTelephone
				+ "]";
	}

	public boolean checkPerson(){
		return pseudo != null && job != null && lastName != null && personEmail != null;
	}
	
}