package fr.sii.atlantique.siistem.notification.scheduler.model.event;

import java.io.Serializable;


/**
 * Class Person, with JPA
 * @author Dorian Coqueron & Pierre Gaultier
 * @version 1.0
 */
public class Person implements Serializable{

	private static final long serialVersionUID = 7525202378849284784L;
	private int personID;
	private String pseudo;
	private String lastName;
	private String firstName;
	private String job;
	private String personEmail;
	private String personTelephone;
	
	public Person(String pseudo, String job, String personTelephone ) {
		this.pseudo = pseudo;
		this.job = job;
		this.personTelephone = personTelephone;
	}

	public Person(){
		//JPA need empty constructor
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