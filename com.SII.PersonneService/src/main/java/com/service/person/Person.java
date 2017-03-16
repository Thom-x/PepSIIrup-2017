package com.service.person;

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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PersonID",unique=true, nullable=false)
	private int personID;

	@Column(name = "Pseudo")
	private String pseudo;

	@Column(name = "Job")
	private String job;

	public Person(String pseudo, String job) {
		this.pseudo = pseudo;
		this.job = job;
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

	public String getJob(){
		return this.job;
	}

	public void setString(String job){
		this.job = job;
	}

	@Override
	public String toString() {
		return "Person [personID=" + personID + ", pseudo=" + pseudo + ", job=" + job + "]";
	}
	
}