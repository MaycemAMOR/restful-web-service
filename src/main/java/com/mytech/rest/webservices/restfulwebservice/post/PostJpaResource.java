package com.mytech.rest.webservices.restfulwebservice.post;

import com.mytech.rest.webservices.restfulwebservice.jpa.PostRepository;
import com.mytech.rest.webservices.restfulwebservice.jpa.UserRepository;
import com.mytech.rest.webservices.restfulwebservice.user.User;
import com.mytech.rest.webservices.restfulwebservice.user.UserNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class PostJpaResource {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostJpaResource(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrievePostsForAUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("id : " + id);
        return user.get().getPosts();
    }


}
