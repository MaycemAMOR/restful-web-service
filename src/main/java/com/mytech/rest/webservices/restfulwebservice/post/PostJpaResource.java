package com.mytech.rest.webservices.restfulwebservice.post;

import com.mytech.rest.webservices.restfulwebservice.jpa.PostRepository;
import com.mytech.rest.webservices.restfulwebservice.jpa.UserRepository;
import com.mytech.rest.webservices.restfulwebservice.user.User;
import com.mytech.rest.webservices.restfulwebservice.user.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PostJpaResource {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostJpaResource(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/jpa/posts")
    public List<Post> retrieveAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> retrieveAllPostsForAUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("id : " + id);

        return user.get().getPosts();
    }

    @PostMapping("/jpa/users/{id}/posts")
    public ResponseEntity<Post> createAPostForAUser(@Valid @RequestBody Post post, @PathVariable int id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty())
            throw new UserNotFoundException("id : " + id);

        post.setUser(user.get());
        Post savedPost = postRepository.save(post);
        // envoyer location aprés la création de chaque post
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(location).build(); /* amiliorer le code status de retour sucess response => 200
                                                            created status => 201
                                                            no content => 204
                                                            unauthorized (when authorization fails) => 401
                                                            Bad Request (validation error) => 400
                                                            resourcer is not found => 404
                                                            server exception => 500*/
    }

    @GetMapping("/jpa/users/{id_user}/posts/{id_post}")
    public EntityModel<Optional<Post>> retrievePost(@PathVariable int id_user, @PathVariable int id_post) { // wrap the User bean by a EntityModel
        Optional<User> user = userRepository.findById(id_user);
        if (user.isEmpty())
            throw new UserNotFoundException("id : " + id_user);

        Optional<Post> post = postRepository.findById(id_post);
        if (post.isEmpty())
            throw new PostNotFoundException("id : " + id_post);

        EntityModel<Optional<Post>> entityModel = EntityModel.of(post); // create the entity model
        // create links for all posts for the user_id
        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllPostsForAUser(id_user));

        // create links for all Posts
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).retrieveAllPosts());

        //add the link to the entityModel with specific Rel "all-Posts"
        entityModel.add(link2.withRel("all-Posts"));

        //add the link to the entityModel with specific Rel "all-Posts-for-user-" + id_user
        entityModel.add(link.withRel("all-Posts-for-user-" + id_user));
        return entityModel;
    }


}
