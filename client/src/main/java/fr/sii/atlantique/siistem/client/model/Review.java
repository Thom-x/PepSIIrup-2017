package fr.sii.atlantique.siistem.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.io.Serializable;

@JsonRootName("Review")
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("PersonId")
    private int personId;

    @JsonProperty("EventId")
    private int eventId;

    @JsonProperty("Rate")
    private int rate;

    @JsonProperty("Text")
    private String text;

    public int getPersonId() {
        return personId;
    }


    public void setPersonId(int personId) {
        this.personId = personId;
    }


    public int getEventId() {
        return eventId;
    }


    public void setEventId(int eventId) {
        this.eventId = eventId;
    }


    public static long getSerialversionuid() {
        return serialVersionUID;
    }


    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }


    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
