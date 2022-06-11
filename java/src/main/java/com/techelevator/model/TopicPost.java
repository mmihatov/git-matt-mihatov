package com.techelevator.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TopicPost {
    @JsonProperty( "post_id" )
    int postId;
    @JsonProperty( "topic_id" )
    int topicId;
    @NotBlank ( message = "Post cannot be blank" )
    String post;
    @JsonProperty( "username" )
    String postCreatedByUsername;
    @JsonProperty( "topic_date" )
    String PostCreatedDate;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPostCreatedByUsername() {
        return postCreatedByUsername;
    }

    public void setPostCreatedByUsername(String postCreatedByUsername) {
        this.postCreatedByUsername = postCreatedByUsername;
    }

    public String getPostCreatedDate() {
        return PostCreatedDate;
    }

    public void setPostCreatedDate(String postCreatedDate) {
        PostCreatedDate = postCreatedDate;
    }
}
