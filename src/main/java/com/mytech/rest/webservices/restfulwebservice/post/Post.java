package com.mytech.rest.webservices.restfulwebservice.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mytech.rest.webservices.restfulwebservice.user.User;
import jakarta.persistence.*;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    //Type Fetch is going to FetchType.LAZY. When we fetch the post, we don't really want
    // to fetch the user details that are associated with the post.
    @JsonIgnore // pour ne afficher le users details dans le response bean de chaque post
    private User user;


    public Post(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Post() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
