package fr.sii.atlantique.siistem.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Comment {

    @JsonProperty("CommentId")
    private String id;

    @JsonProperty("Text")
    private String text;

    @JsonProperty("DatePost")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone="CET")
    private Date datePost;

    @JsonProperty("Person")
    private Person person;

    @JsonProperty("Responses")
    private List<Comment> responses;
}