package com.service.person;

import com.fasterxml.jackson.annotation.JsonRootName;
import javax.persistence.*;

@Entity
@Table(name = "Person")
@JsonRootName("Person")
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PersonID",unique=true, nullable=false)
	private int personID;

	@Column(name = "Pseudo")
	private String pseudo;
	
	@Column(name = "Job")
	private String job;
	
	public Person(){}
	
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
	
	}