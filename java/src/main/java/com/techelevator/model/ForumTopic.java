package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class ForumTopic {

    @JsonProperty("topic_id")
    int topicId;

    @NotBlank ( message = "Topic name cannot be blank" )
    @JsonProperty("topic_name")
    String topicName;

    @JsonProperty("username")
    String CreatedByUsername;

    @JsonProperty("topic_date")
    LocalDate topicCreatedDate;

    @NotBlank( message = "Post cannot be blank" )
    @JsonProperty("initial_post")
    String initialPost;

    public ForumTopic() {}

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getCreatedByUsername() {
        return CreatedByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        CreatedByUsername = createdByUsername;
    }

    public LocalDate getTopicCreatedDate() {
        return topicCreatedDate;
    }

    public void setTopicCreatedDate(LocalDate topicCreatedDate) {
        this.topicCreatedDate = topicCreatedDate;
    }

    public String getInitialPost() {
        return initialPost;
    }

    public void setInitialPost(String initialPost) {
        this.initialPost = initialPost;
    }
}