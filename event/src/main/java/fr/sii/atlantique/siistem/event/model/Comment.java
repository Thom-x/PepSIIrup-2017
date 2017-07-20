package fr.sii.atlantique.siistem.event.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Comment {

    @Id
    @JsonProperty("CommentId")
    private String id;

    @JsonProperty("Text")
    private String text;

    @JsonProperty("DatePost")
    private Date datePost;

    @JsonProperty("Person")
    private Person person;

    @JsonProperty("Responses")
    private List<Comment> responses;
}