package com.mytech.rest.webservices.restfulwebservice.jpa;

import com.mytech.rest.webservices.restfulwebservice.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
