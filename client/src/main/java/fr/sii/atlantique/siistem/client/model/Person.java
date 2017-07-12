package fr.sii.atlantique.siistem.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;


/**
 * Class Person, with JPA
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
@JsonRootName("Person")
public class Person implements Serializable{

	private static final long serialVersionUID = 7525202378849284784L;

	@JsonProperty("PersonId")
	private int personID;

	@JsonProperty("Pseudo")
	private String pseudo;
	
	@JsonProperty("LastName")
	private String lastName;
	
	@JsonProperty("FirstName")
	private String firstName;

	@JsonProperty("Job")
	private String job;

	@JsonProperty("PersonEmail")
	private String personEmail;
	
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

	@Override
	public String toString() {
		return "Person [personID=" + personID + ", pseudo=" + pseudo + ", lastName=" + lastName + ", firstName="
				+ firstName + ", job=" + job + ", personEmail=" + personEmail + "]";
	}

	
	public boolean checkPerson(){
		return pseudo != null && job != null && lastName != null && personEmail != null;
	}
	
}